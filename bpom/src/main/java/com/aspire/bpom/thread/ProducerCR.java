package com.aspire.bpom.thread;

import org.slf4j.Logger;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ReconciliationService;

/**
 * 签约对账生产者
 * @author liuweifeng
 *
 */
public class ProducerCR implements Runnable {
	private static final Logger logger = BpomLogger.getLogger(ProducerCR.class);

	private ReconciliationService contractReconService;
	
	public ProducerCR(ReconciliationService contractReconService){
		this.contractReconService = contractReconService;
	}
	
	@Override
	public void run() {
		try {
			contractReconService.produce();
		} catch (Exception e) {
			logger.error("ProducerCR.run().error" + e);
			e.printStackTrace();
		}
	}
}
