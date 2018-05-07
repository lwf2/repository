package com.aspire.bpom.task;

import java.io.File;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ReconciliationService;
import com.aspire.bpom.thread.ConsumerOR;
import com.aspire.bpom.thread.ProducerOR;
import com.aspire.bpom.util.FTPUtil;
import com.aspire.bpom.util.SystemManager;

/**
 * 订单对账定时任务
 * @author liuweifeng
 *
 */
public class OrderReconTask {
	
	private static final Logger logger = BpomLogger.getLogger(OrderReconTask.class);

	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Resource
	private ReconciliationService orderReconService;
	
	@Resource
	private SystemManager sm;
	
	public void work(){
		try {
			logger.info("订单对账和订单结算定时任务启动");
			logger.info("调用FTP下载上传开始");
			String shellPath = sm.getShellPath();
			String downloadShellFile = shellPath+File.separator+"ordercheckget.sh";
			String uploadShellFile =shellPath+File.separator+"ordercheckbackupput.sh" ;
			FTPUtil.downloadFile(downloadShellFile, uploadShellFile);
			logger.info("调用FTP下载上传结束");
			CountDownLatch latch = new CountDownLatch(1);
			//FtpHelper.download("lwf", "xiaopang.csv", "d:/xiaopang/xiaopang.csv", "ftp");
			taskExecutor.execute(new ProducerOR(orderReconService));
			taskExecutor.execute(new ConsumerOR(orderReconService, latch));
			latch.await();
			orderReconService.dealFile();
			logger.info("订单对账和订单结算定时任务结束");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("OrderReconTask.work().error" + e);
		}
	}
	
}
