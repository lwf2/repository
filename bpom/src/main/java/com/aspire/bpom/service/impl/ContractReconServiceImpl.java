package com.aspire.bpom.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.aspire.bpom.dao.SignDao;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ReconciliationService;
import com.aspire.bpom.thread.ConsumerCR;
import com.aspire.bpom.util.FTPUtil;
import com.aspire.bpom.util.FileUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.SignRelationshipReq;

/**
 * 签约对账处理类
 * @author liuweifeng
 *
 */
@Service("contractReconService")
public class ContractReconServiceImpl implements ReconciliationService {
	private static final Logger logger = BpomLogger.getLogger(ContractReconServiceImpl.class);

	@Resource
	private SystemManager sm;

	@Resource
	private SignDao signDao;
	/**
	 * 最大批量更新数量
	 */
	private final int MAX_SIZE = 500;

	/**
	 * 无界阻塞队列，同步存入取出数据
	 */
	private LinkedBlockingQueue<SignRelationshipReq> list = new LinkedBlockingQueue<SignRelationshipReq>(100);
		
	private List<SignRelationshipReq> signReqList = new ArrayList<SignRelationshipReq>();
	
	private SignRelationshipReq signReq = null;
	
	private Integer status = null;
	
	@Override
	public void produce() throws Exception {

		int count = FileUtil.readCSVFile(new SignRelationshipReq(),
				sm.getContractdownloadPath(), list) + 1;
		SignRelationshipReq sign = new SignRelationshipReq();
		sign.setCount(count);
		list.put(sign);
	}

	@Override
	public boolean consume() throws Exception {			
		// 获取的数据超过限定条数，就批量更新到数据库
		if (signReqList.size() >= MAX_SIZE) {
			batchUpdateSign();//批量更新
		}
		signReq = list.take();
	    if (ConsumerCR.i == signReq.getCount()) {
	    	if(signReqList.size() != 0){
	    		batchUpdateSign();//最后批量更新
	    	}	    				
	    	return true;
		}
	    if (!StringUtils.isBlank(signReq.getWxContract_code())) {
			if(validataSignPO()){
				if("0".equals(signReq.getContract_state())){
					signReq.setContract_state("1");
				}else if("1".equals(signReq.getContract_state())){
					signReq.setContract_state("2");
				}
				signReqList.add(signReq);
			}				
		}
		return false;
	}
	
	/**
	 * 验证微信签约表数据与支付网关的数据是否一致
	 * @param signReq
	 * @return
	 */
	private boolean validataSignPO() {
		boolean flag = false;
		status = signDao.getSignEntity(signReq.getWxContract_code());
		if (null != status) {
			if (!(signReq.getContract_state().equals("0") && status.equals(1))
					&& !(signReq.getContract_state().equals("1") && status
							.equals(2))) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * 批量更新
	 * @throws Exception
	 */
	private void batchUpdateSign() throws Exception {
		logger.info("开始批量更新");
		signDao.batchUpdateSign(signReqList);
		signReqList.removeAll(signReqList);//清空集合数据
		logger.info("结束批量更新");
	}
	
	
	@Override
	public void dealFile() throws Exception{		
		Thread.sleep(2000);//暂停2秒开始准备移动需要上传的文件
		logger.info("移动签约对账文件开始");
		moveFileAndModifyName(sm.getContractdownloadPath(), sm.getContractresultPath());
		logger.info("移动签约对账文件结束");
		Thread.sleep(2000);//暂停2秒开始准备上传的文件
		logger.info("上传签约对账csv文件开始！");
		String shellPath = sm.getShellPath();
		String uploadPath = shellPath+File.separator+"uploadcontract.sh";
		if(FTPUtil.excelUploadShell(uploadPath)){
			logger.info("文件上传成功将文件移到备份目录");
			FileUtil.moveFile(sm.getContractresultPath(), sm.getContractresultbackUpPath());
		}else{
			logger.error("执行上传shell脚本失败。");
		}
	}
	
	/**
	 * 移动文件并修改名称
	 * @param oldPath
	 * @param newPath
	 * @throws Exception
	 */
	private void moveFileAndModifyName (String oldPath, String newPath) throws Exception {
		File[] oldFileArr = new File(oldPath).listFiles();
		String fileName = null;
		if (oldFileArr != null) {
			for (int i = 0; i < oldFileArr.length; i++) {
				fileName = oldFileArr[i].getName();
				fileName = fileName.replace("wxEntrustPay", "wxContract");
				if (oldFileArr[i].renameTo(new File(newPath + "/" + fileName))) {  
					logger.info(oldFileArr[i].getName() + "文件移动成功！");
				} else {  
					logger.error(oldFileArr[i].getName() + "文件移动失败！");
					throw new Exception(oldFileArr[i].getName() + "文件移动失败！");
				}
			}
		} else {
			logger.error("文件目录下无文件。");
		}
	}
}
