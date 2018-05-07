package com.aspire.bpom.thread;

import org.slf4j.Logger;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.ReconciliationService;

public class ProducerOR extends Thread {
	
	private static final Logger logger = BpomLogger.getLogger(ProducerOR.class);

	private ReconciliationService orderReconService;
	
	public ProducerOR(ReconciliationService orderReconService) {
		super();
		this.orderReconService = orderReconService;
	}

	@Override
	public void run() {
		try {
			orderReconService.produce();
		} catch (Exception e) {
			logger.error("ProducerOR.run().error" + e);
			e.printStackTrace();
		}
	}
}
