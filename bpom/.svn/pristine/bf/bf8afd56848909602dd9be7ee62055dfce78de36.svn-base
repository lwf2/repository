package com.aspire.bpom.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.bean.SettlePO;
import com.aspire.bpom.dao.OrderDao;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ReconciliationService;
import com.aspire.bpom.thread.ConsumerOR;
import com.aspire.bpom.util.FTPUtil;
import com.aspire.bpom.util.FileUtil;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.PayTransReconReq;
import com.aspire.bpom.xml.bean.response.OrderReconResp;

/**
 * 订单对账和订单结算处理类
 * @author liuweifeng
 *
 */
@Service("orderReconService")
public class OrderReconServiceImpl implements ReconciliationService {

	private static final Logger logger = BpomLogger.getLogger(OrderReconServiceImpl.class);
			
	@Resource
	private SystemManager sm;
	
	@Resource
	private OrderDao orderDao;
	/**
	 * 最大批量更新数量
	 */
	private final int MAX_SIZE = 500;

	/**
	 * 无界阻塞队列，同步存入取出数据
	 */
	private LinkedBlockingQueue<PayTransReconReq> list = new LinkedBlockingQueue<PayTransReconReq>(100);

	/**
	 * 生成csv文件头部表头
	 */
	//private String[] headArr = {"业务平台ID", "订单号", "交易类型", "外部交易ID", "提交支付时间", "第三方支付机构代码", "支付通道代码", "支付方式代码", "交易金额", "交易完成时间"};
	
    /**
     * 生成csv文件尾部表头	
     */
	//private String[] headArr2 = {"总记录数", "支付记录条数", "支付金额", "退款记录条数", "退款金额"};
	
	/**
	 * 存储读取从支付网关来的支付交易文件数据
	 */
	private List<PayTransReconReq> payTranList = new ArrayList<PayTransReconReq>();
	
	/**
	 * 存储生成订单对账文件的数据
	 */
	private List<OrderReconResp> orderList = new ArrayList<OrderReconResp>();
	
	/**
	 * 存储生成订单结算文件的数据
	 */
	private List<SettlePO> settleList = new ArrayList<SettlePO>();
	
	/**
	 * 存储查询订单对账条件的数据
	 */
	private Set<String> set = new HashSet<String>();
	
	private List<OrderPO> orderPoList = null;
	
	private PayTransReconReq payTran = null;
	
	private OrderReconResp order = null;
	
	private String transType = null;
	
	private OrderPO orderPo = null;
	
	private SettlePO settlePo = null;

	@Override
	public void produce() throws Exception {
		int count = FileUtil.readCSVFile(new PayTransReconReq(),
				sm.getOrderdownloadPath(), list) + 1;
		PayTransReconReq payTran = new PayTransReconReq();
		payTran.setCount(count);
		list.put(payTran);		
	}

	@Override
	public boolean consume() throws Exception {
		//获取的数据超过限定条数，就批量更新到数据库
		if(payTranList.size() >= MAX_SIZE){
			batchOperationData();//做数据操作处理
		}
		//当存储的数据大于等于设定的数量时，生成订单结算文件
		if(settleList.size() >= sm.getOrderCountLimitNumber()){
			generateSettleFile();//生成订单结算文件
		}
		payTran = list.take();
		if(ConsumerOR.i == payTran.getCount()){
			if(payTranList.size() != 0){
				batchOperationData();//最后做数据操作处理
			}
			if(settleList.size() != 0){
				generateSettleFile();//最后生成订单结算文件
			}
			readerAndGenerateFile();//生成订单对账文件
			return true;
		}
		//过滤尾部数据
		if(!StringUtils.isBlank(payTran.getPayOrganization())){									
			getData();
		}		
		return false;
	}

	/**
	 * 批量操作数据
	 * @throws Exception
	 */
	private void batchOperationData() throws Exception {
		logger.info("批量更新订单表开始");
		orderDao.batchUpdateOrder(payTranList);//批量更新订单表
		logger.info("批量更新订单表结束");
		logger.info("批量查询订单表开始");
		orderPoList = orderDao.batchQueryOrder(payTranList);//批量查询已更新到订单表的数据
		logger.info("批量查询订单表结束");
		logger.info("批量新增订单历史表开始");
		orderDao.batchAddOrderHis(orderPoList);//批量新增订单历史记录表
		logger.info("批量新增订单历史表结束");
		payTranList.removeAll(payTranList);//清空更新数据
	}
	
	/**
	 * 添加更新订单表和新增订单记录表的数据并添加订单结算数据
	 * @throws Exception
	 */
	private void getData() throws Exception {
		set.add(payTran.getOrderId());//添加查询订单对账条件的数据
		// 根据外部订单ID查询数据
		orderPo = orderDao.getOrderByOutOrderId(payTran.getOrderId());
		transType = payTran.getTransType();
		if(null != orderPo.getPayStatus()){
			// 验证从支付网关的数据和计费平台订单表的数据是否一致，否则则添加到list中，更新计费平台订单表的数据。
			if (!(payTran.getTransType().equals("P") && (orderPo.getPayStatus().equals(20) || orderPo.getPayStatus()
					.equals(70)))
					&& !(payTran.getTransType().equals("R") && orderPo.getPayStatus().equals(70))) {
				if ("P".equals(transType)) {
					payTran.setTransType("20");// 订单表的支付成功状态
				} else if ("R".equals(transType)) {
					payTran.setTransType("70");// 订单表的退款成功状态
				}
				payTranList.add(payTran);
			}
		}
		//订单结算
		if("P".equals(payTran.getTransType())){
			ConsumerOR.k++;//支付记录条数
			ConsumerOR.l += payTran.getTotalFee();//支付金额
		}else if("R".equals(payTran.getTransType())){
			ConsumerOR.m++;//退款记录条数
			ConsumerOR.n += payTran.getTotalFee();//退款金额
		}
		ConsumerOR.j++;//总记录数
		//添加订单结算数据
		settlePo = new SettlePO();
		settlePo.setOrderId(orderPo.getOutOrderId());//外部订单号
		settlePo.setTradeId(payTran.getOrderId());//外部交易ID
		settlePo.setUserId(orderPo.getUserId());//用户ID
		settlePo.setProductId(orderPo.getProductId());//产品代码
		settlePo.setChannelId(orderPo.getChannelId());//渠道代码
		settlePo.setTotalFee(String.valueOf(payTran.getTotalFee()));//交易金额
		settlePo.setTransType(payTran.getTransType());//交易类型 P:付款；R:退款
		settlePo.setRequestId(payTran.getRequestId());//支付网关交易ID
		settlePo.setSubmitTime(payTran.getSubmitTime());//提交支付时间
		settlePo.setPayOrganization(payTran.getPayOrganization());//第三方支付机构代码
		settlePo.setPayWap(payTran.getPayWap());//支付通道代码
		settlePo.setPayType(payTran.getPayType());//支付方式代码
		settlePo.setPayTime(payTran.getPayTime());//交易完成时间
		settleList.add(settlePo);		
	}
	
	/**
	 * 查询订单对账的数据，并生成订单对账文件
	 */
	private void readerAndGenerateFile() throws Exception {
		int j = 0;// 总记录数
		int k = 0;// 支付记录条数
		int l = 0;// 支付金额
		int m = 0;// 退款记录条数
		int n = 0;// 退款金额
		int o = 0;//用于生成文件名称的数字序号
		logger.info("查询订单对账数据开始");
		List<OrderPO> orderPoList2 = orderDao.getOrderListByTradeId(set);
		logger.info("查询订单对账数据结束");
		for (OrderPO orderPo : orderPoList2) {
			if(orderPo.getPayStatus().equals(20)){
				k++;//支付记录条数
				l += orderPo.getAmount();//支付金额
			}else if(orderPo.getPayStatus().equals(70)){
				m++;//退款记录条数
				n += orderPo.getAmount();//退款金额
			}
			j++;//总记录数
			//封装生成订单对账文件的数据
			order = new OrderReconResp();
			order.setServPltfmCode(orderPo.getServPltfmCode());//业务平台ID
			order.setOrderId(orderPo.getOutOrderId());;//外部订单号
			order.setTransType(orderPo.getPayStatus().equals(20) ? "P" : "R");//交易类型
			order.setTradeId(orderPo.getTradeId());;//外部交易ID
			order.setSubmitTime(orderPo.getOrderTime());//提交支付时间
			order.setPayOrganization(orderPo.getPayOrganization());//第三方支付机构代码
			order.setPayWap(orderPo.getPayWay());//支付通道代码
			order.setPayType(String.valueOf(orderPo.getPayType()));//支付方式代码
			order.setTotalFee(String.valueOf(orderPo.getAmount()));//交易金额
			order.setPayTime(orderPo.getPayStatus().equals(20) ? orderPo.getPayTime() : orderPo.getOperateTime());//交易完成时间
			orderList.add(order);
			//当生成订单对账文件行数大于等于设定条数时，就生成文件
			if(orderList.size() >= sm.getOrderLimitNumber()){
				generateFile(j, k, l, m, n, ++o);
				//清空记录
				j = 0;// 总记录数
				k = 0;// 支付记录条数
				l = 0;// 支付金额
				m = 0;// 退款记录条数
				n = 0;// 退款金额
			}
		}
		if(orderList.size() < sm.getOrderLimitNumber() && orderList.size() != 0){
			generateFile(j, k, l, m, n, ++o);
		}
	}
	
	/**
	 * 生成订单结算文件
	 * @throws Exception
	 */
	private void generateSettleFile() throws Exception {
		logger.info("生成订单结算文件开始");
		logger.info("总记录数==" + ConsumerOR.j + " 支付记录条数==" + ConsumerOR.k 
				+ " 支付金额==" + ConsumerOR.l + " 退款记录条数==" + ConsumerOR.m + " 退款金额==" + ConsumerOR.n);
		//生成文件名称
		String fileName = FileUtil.getFileName("rwkTradeList", ++ConsumerOR.o);
		logger.info("生成csv文件名称为 fileName=" + fileName);
		//结算文件尾部统计数据
		String[] dataCount = {String.valueOf(ConsumerOR.j), String.valueOf(ConsumerOR.k), String.valueOf(ConsumerOR.l), 
				String.valueOf(ConsumerOR.m), String.valueOf(ConsumerOR.n)};
		//生成结算文件
		logger.info("生成文件存放目录为：" + sm.getOrderCountPath());
		boolean flag = FileUtil.generateCSVFile(sm.getOrderCountPath(), fileName, settleList, dataCount);
		if(!flag){
			throw new Exception("OrderReconServiceImpl.generateSettleFile().error 订单结算生成"+ fileName +"文件失败");
		}
		settleList.removeAll(settleList);//清空数据
		//清空记录
		ConsumerOR.j = 0;// 总记录数
		ConsumerOR.k = 0;// 支付记录条数
		ConsumerOR.l = 0;// 支付金额
		ConsumerOR.m = 0;// 退款记录条数
		ConsumerOR.n = 0;// 退款金额
		logger.info("生成订单结算 "+ fileName +"文件结束");
	}
	
	/**
	 * 生成订单对账文件
	 * @throws Exception
	 */
	private void generateFile(int j, int k, int l, int m, int n, int o) throws Exception {
		logger.info("生成订单对账文件开始");
		logger.info("总记录数==" + j + " 支付记录条数==" + k 
				+ " 支付金额==" + l + " 退款记录条数==" + m + " 退款金额==" + n);
		//生成文件名称
		String fileName = FileUtil.getFileName("PayList", "RWK", o);
		logger.info("生成csv文件名称为 fileName=" + fileName);
		//订单对账文件尾部统计数据
		String[] dataCount = {String.valueOf(j), String.valueOf(k), String.valueOf(l), 
				String.valueOf(m), String.valueOf(n)};
		//生成订单对账文件
		logger.info("生成文件存放目录为："+sm.getOrderresultPath());
		boolean flag = FileUtil.generateCSVFile(sm.getOrderresultPath(), fileName, orderList, dataCount);
		if(!flag){
			throw new Exception("OrderReconServiceImpl.generateCSVFile().error 订单对账生成"+ fileName +"文件失败");
		}
		orderList.removeAll(orderList);//清空数据		
		logger.info("生成订单对账 "+ fileName +"文件结束");
	}
	
	@Override
	public void dealFile() throws Exception{		
		Thread.sleep(2000);//暂停2秒开始准备上传文件到sftp服务器
		logger.info("上传订单对账和订单结算文件开始！");
		String shellPath=sm.getShellPath();
		String shellFilePath = shellPath+File.separator+"uploadordercheck.sh";//订单对账脚本路径
		String shpath = shellPath + File.separator + "uploadordercount.sh";//订单结算脚本路径
		if(FTPUtil.excelUploadShell(shellFilePath)){
			logger.info("文件上传成功将文件移到备份目录");
			FileUtil.moveFile(sm.getOrderresultPath(), sm.getOrderresultbackupPath());				
		}else{
			logger.error("执行上传订单结账shell脚本失败。");
		}
		if(FTPUtil.excelUploadShell(shpath)){
			logger.info("将订单结算生成的文件移到备份目录");
			FileUtil.moveFile(sm.getOrderCountPath(), sm.getOrderCountBackUpPath());
		}else{
			logger.error("执行上传订单结算shell脚本失败。");
		}
		logger.info("将支付网关的文件移到备份目录");
		FileUtil.moveFile(sm.getOrderdownloadPath(), sm.getOrderbackUpPath());
		logger.info("上传订单对账和订单结算文件结束！");
	}
}
