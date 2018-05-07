package com.aspire.bpom.task;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ReconciliationService;
import com.aspire.bpom.thread.ConsumerCR;
import com.aspire.bpom.thread.ProducerCR;
import com.aspire.bpom.util.FTPUtil;
import com.aspire.bpom.util.SystemManager;

/**
 * 签约对账定时任务
 * @author liuweifeng
 *
 */
public class ContractReconTask {
	
	private static final Logger logger = BpomLogger.getLogger(ContractReconTask.class);
	
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	@Resource
	private ReconciliationService contractReconService;
	
	@Resource
	private SystemManager sm;

	public void work(){
		FileInputStream in = null;
		try {
			logger.info("签约对账定时任务启动");
			logger.info("调用FTP下载上传开始");
//			FTPUtil.downloadFile("/home/cc_qyw/shell/downloadContractReconFile.sh", "/home/cc_qyw/shell/uploadBashShellFile.sh");
//			downloadFile();
			String shellPath = sm.getShellPath();
			String downloadPath = shellPath + File.separator+"contractcheckget.sh";
			String uploadPath = shellPath + File.separator+"contractcheckbackupput.sh";
			FTPUtil.downloadFile(downloadPath, uploadPath);
			
			logger.info("调用FTP下载上传结束");
			CountDownLatch latch = new CountDownLatch(1);
			//in = new FileInputStream(new File("C:/Users/liuweifeng/Desktop/wxEntrustPay_20170925_5544_NNN.csv"));
			//FtpHelper.upload("lwf", "xiaopang.csv", in, "ftp", false);
			taskExecutor.execute(new ProducerCR(contractReconService));
			taskExecutor.execute(new ConsumerCR(contractReconService, latch));
			latch.await();
			contractReconService.dealFile();
			logger.info("签约对账定时任务结束");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("ContractReconTask.work().error" + e);
		} finally {
			try {
				if(in != null){
					in.close();
				}				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
