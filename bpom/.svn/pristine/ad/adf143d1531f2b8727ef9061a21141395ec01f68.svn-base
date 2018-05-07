package com.aspire.bpom.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.SettlePO;
import com.aspire.bpom.dao.OrderDao;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.OrderCountService;
import com.aspire.bpom.util.DateUtil;
import com.aspire.bpom.util.FileUtil;
import com.aspire.bpom.util.SystemManager;

/**
 * 生成结算文件
 * @author chenpeng
 *
 */
@Component("orderCountService")
public class OrderCountServiceImpl implements OrderCountService{

	private static final Logger log = BpomLogger.getLogger(OrderCountServiceImpl.class);
	
	@Resource
	private SystemManager sm;
	
	@Resource
	private OrderDao orderDao;
	
	private int m = 0;// 分割文件数目，用于命名生成的csv文件
	
	/**
     * 生成csv文件头部表头
     *//*
    private String[] headArr = {"订单号", "外部交易ID", "用户ID", "产品代码", "渠道代码", "交易金额", "交易类型", "协议解约方式", "支付网关交易ID", "提交支付时间", "第三方支付机构代码", "支付通道代码", "支付方式代码", "交易完成时间"};
    
    *//**
     * 生成csv文件尾部表头  
     *//*
    private String[] headArr2 = {"总记录数", "支付记录条数","支付金额", "退款记录条数", "退款金额"};*/
    
    @Override
    public void consume() throws Exception {
        List<SettlePO> settlePOList= null;
        List<SettlePO> list=new ArrayList<SettlePO>();
        try
        {
            settlePOList=orderDao.timingQuerySettle();
            int j = 0;// 总记录数
            int k = 0;// 支付记录条数
            int l = 0;// 退款记录条数
            int payFee=0;
            int refundFee=0;
            m = 0;
            for(SettlePO settlePO : settlePOList){
                //查询出来的数据大于等于设置的分割数据，生成多个文件
                if("P".equals(settlePO.getTransType())){
                    k++;// 支付记录条数
                    payFee+=Integer.parseInt(settlePO.getTotalFee());
                }
                if("R".equals(settlePO.getTransType())){
                    l++;//退款记录条数
                    refundFee+=Integer.parseInt(settlePO.getTotalFee());
                }
                j++;//总记录数
                list.add(settlePO);
                if(list.size() >= sm.getOrderCountLimitNumber()){                                      
                    generateCSVFile(list, j, k, l,payFee,refundFee);//生成csv文件                
                    list.removeAll(list);
                    //清空记录
                    j = 0;
                    k = 0;
                    l = 0;
                    payFee=0;
                    refundFee=0;
                }
            }   
            /*
             * 查询出来的数据小于设置的分割数据，生成一个文件
             * 分割后的数据小于设置的分割数据，生成最后一个文件
             * 
             */
            if(list.size() < sm.getOrderCountLimitNumber() && list.size() != 0){
                generateCSVFile(list, j, k, l,payFee,refundFee);//生成csv文件
            }
            
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            
        }finally{
            //上传文件到sftp服务器并将文件移到备份目录
            dealFile();
        }
        
    }
    /**
     * 生成csv文件
     * @param settlePOList csv文件主体数据
     * @param j 总记录数
     * @param k 支付记录条数
     * @param l 退款记录条数
     * @param m 分割成第几个文件，也用于命名规则中
     * @throws Exception
     */
    private void generateCSVFile(List<SettlePO> settlePOList, 
            int j, int k, int l,int payFee,int refundFee) throws Exception {
        log.info("生成csv文件开始");
        //生成文件名称
        String fileName =getFileName("rwkTradeList", ++m);
        log.info("生成csv文件名称为 fileName=" + fileName);
        //csv文件尾部统计数据
        String[] dataCount = {String.valueOf(j), String.valueOf(k),String.valueOf(payFee), 
                        String.valueOf(l),String.valueOf(refundFee)};
        //生成csv文件
        boolean flag = FileUtil.generateCSVFile(sm.getOrderCountPath(), fileName, settlePOList, dataCount);
        if(!flag){
            log.error("ContractReconServiceImpl.generateCSVFile().error 签约对账生成"+ fileName +"文件失败");
            System.out.println("建议把文件做个迁移");
        }
        log.info("生成 "+ fileName +"文件结束");
    }
    
    /**
     * 获取生成csv文件名称
     * 
     * @param baseName
     *            前缀基本名称
     * @param num
     *            第几个文件
     * @return
     */
    public static String getFileName(String baseName,int num) {
        String fileNameNum = null;
        StringBuffer buffer = new StringBuffer(baseName);
        buffer.append("_");
        buffer.append(DateUtil.getTimeStamp(new Date(), "yyyyMMdd"));
        buffer.append("_");
        if (num >= 10) {
            fileNameNum = "0" + num;
        } else if (num >= 100) {
            fileNameNum = String.valueOf(num);
        } else {
            fileNameNum = "00" + num;
        }
        buffer.append(fileNameNum);
        buffer.append(".txt");
        return buffer.toString();
    }
	
	/**
	 * 上传文件到sftp服务器并将文件移到备份目录
	 */
	private void dealFile() {
		log.info("生成给任我看的数据接口文件结束。。");
		try {
			Thread.sleep(2000);//暂停2秒开始准备上传文件到sftp服务器
			log.info("开始上传生成的文件...");
			boolean b = excelUploadShell();
			if (b) {
				//文件解析存入临时表后迁移文件到backup目录
				log.info("文件上传成功将文件移到备份目录");
				renameToBackUpFile();
			}else{
				log.error("执行上传shell脚本失败。");
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 执行上传shell脚本将生成的文件上传到sftp服务器
	 */
	private boolean excelUploadShell(){
		boolean flag = false;
		String resultpath = sm.getShellPath();
		String shpath = resultpath+File.separator+"uploadordercount.sh";		//本地目录shell脚本存放路径
		log.info("shpath---"+shpath);
		try {
			Process process = Runtime.getRuntime().exec(shpath);
			//执行shell脚本
			int i = process.waitFor();
			if (i != 0) {	//调用shell脚本判断是否正常执行，正常结束i返回0										
				log.info("call shell failed.error code is :"+i);
			} else {
				log.info("call shell success. code is :"+i);
				log.info("文件上传完毕！");
				flag = true;
			}

			InputStreamReader is = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(is);
			String line;
			while((line = input.readLine()) != null) {
				log.info(line);
			}
			input.close();
			is.close();
		} catch (IOException e) {
			log.error("执行上传脚本失败",e);	
		} catch (InterruptedException e) {
			log.error("执行上传脚本失败",e);
		}
		
		return flag;
	}
	
	/**
	 * 将文件移到备份目录
	 */
	private void renameToBackUpFile() {
		String youshuFilePath = sm.getOrderCountPath();
		String backupPath = sm.getOrderCountBackUpPath();
		//源目录
		File file = new File(youshuFilePath);
		//迁移目录
		File backup = new File(backupPath);
		if (!file.isDirectory() || !backup.isDirectory()) {
			log.info("文件目录不是有效的目录！");
		} else {
			File[] tempList = file.listFiles();
			log.info("该目录下文件个数："+tempList.length);
			for (int i = 0; i < tempList.length; i++) {
				if (tempList[i].isFile()) {
					File file1 = tempList[i];
					File backUpFile = new File(backupPath+File.separator+file1.getName());
					boolean b = file1.renameTo(backUpFile);
					if (b) {
						log.info("----移动文件到备份目录成功"+b);
					} else {
						log.info("----移动文件到备份目录失败"+b);
					}	
				}
			}
		}
	}
}
