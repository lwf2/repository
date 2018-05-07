package com.aspire.bpom.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import org.slf4j.Logger;
import com.aspire.bpom.extensions.log4j.BpomLogger;

public class FTPUtil {

	private static final Logger logger = BpomLogger.getLogger(FTPUtil.class);
	
	/**
	 * 执行上传shell脚本将生成的文件上传到sftp服务器
	 * @param uploadScriptFile 本地目录执行上传shell脚本存放路径
	 * @return
	 */
	public static boolean excelUploadShell(String uploadShellFile) {
		boolean flag = false;
		logger.info("uploadShellFile---" + uploadShellFile);
		try {
			Process process = Runtime.getRuntime().exec(uploadShellFile);
			// 执行shell脚本
			int i = process.waitFor();
			if (i != 0) { // 调用shell脚本判断是否正常执行，正常结束i返回0
				logger.info("call shell failed.error code is :" + i);
			} else {
				logger.info("call shell success. code is :" + i);
				logger.info("文件上传完毕！");
				flag = true;
			}

			InputStreamReader is = new InputStreamReader(
					process.getInputStream());
			LineNumberReader input = new LineNumberReader(is);
			String line;
			while ((line = input.readLine()) != null) {
				logger.info(line);
			}
			input.close();
			is.close();
		} catch (IOException e) {
			logger.error("执行上传脚本失败", e);
		} catch (InterruptedException e) {
			logger.error("执行上传脚本失败", e);
		}
		return flag;
	}

	/**
	 * 下载文件到本地，上传下载的文件到目标服务器的备份目录
	 * @param downloadScriptFile 本地目录执行下载shell脚本存放目录
	 * @param uploadScriptFile 本地目录执行上传shell脚本存放目录
	 */
	public static void downloadFile(String downloadShellFile, String uploadShellFile) {
		try {
				logger.info("-----开始下载对账文件---");
				logger.info("downloadScriptFile---" + downloadShellFile);
				Process process = Runtime.getRuntime().exec(downloadShellFile); // 执行shell脚本
				int i = process.waitFor();
				if (i != 0) { // 调用shell脚本判断是否正常执行，正常结束i返回0
					logger.info("call shell failed.error code is :" + i);
				} else {
					logger.info("call shell success. code is :" + i);
					logger.info("从目标服务器下载文件完毕。。。");
				}
				InputStreamReader is = new InputStreamReader(
						process.getInputStream());
				LineNumberReader input = new LineNumberReader(is);
				String line;
				while ((line = input.readLine()) != null) {
					logger.info(line);
				}
				input.close();
				is.close();

				Thread.sleep(100);
				logger.info("上传备份文件至目标服务器的备份目录");
				logger.info("uploadScriptFile---" + uploadShellFile);
				Process process1 = Runtime.getRuntime().exec(uploadShellFile); // 执行shell脚本
				int j = process1.waitFor();
				if (j != 0) { // 调用shell脚本判断是否正常执行，正常结束j返回0
					logger.info("call shell failed.error code is :" + j);
				} else {
					logger.info("call shell success. code is :" + j);
					logger.info("上传备份文件至目标服务器的备份目录完毕。。。");
				}
				InputStreamReader isr = new InputStreamReader(
						process1.getInputStream());
				LineNumberReader input1 = new LineNumberReader(isr);
				String line1;
				while ((line1 = input1.readLine()) != null) {
					logger.info(line1);
				}
				input1.close();
				isr.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			logger.error("call shell faileded" + e);
		}
	}

	public static void main(String[] args) {
		FTPUtil.excelUploadShell("D:/xiaopang/shell/uploadContractReconFile.sh");			
	}
}
