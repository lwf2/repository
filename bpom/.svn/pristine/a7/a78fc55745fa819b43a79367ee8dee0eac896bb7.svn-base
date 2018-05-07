package com.aspire.bpom.thread;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.queue.ContractNotifyQueue;
import com.aspire.bpom.queue.PayResultNotifyQueue;
import com.aspire.bpom.queue.RefundRsltNotifyQueue;
import com.aspire.bpom.task.ContractNotifyTask;
import com.aspire.bpom.task.PayResultNotifyTask;
import com.aspire.bpom.task.RefundRsltNotifyTask;
import com.aspire.bpom.xml.bean.request.EntrustPayNotifyReq;
import com.aspire.bpom.xml.bean.request.PayResultNotifyReq;
import com.aspire.bpom.xml.bean.request.RefundRsltNotifyReq;

@Component
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class NotifyThreadPool implements BeanFactoryAware{
  
	@Resource
	private PayResultNotifyQueue payResultNotifyQueue;   
	@Resource
	private PayResultNotifyTask payResultNotifyTask;   
	@Resource
	private ContractNotifyQueue contractNotifyQueue;   
	@Resource
	private ContractNotifyTask contractNotifyTask; 
	@Resource
	private RefundRsltNotifyQueue refundRsltNotifyQueue;
	@Resource
	private RefundRsltNotifyTask refundRsltNotifyTask; 
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	
	private BeanFactory beanFactory;
	
	/**
	 * 支付结果通知
	 * @param payResultNotifyReq
	 * @param tradeId
	 */
	public void execute(PayResultNotifyReq payResultNotifyReq, String orderId) {
		OrderNotifyThread thread = beanFactory.getBean(OrderNotifyThread.class);
		thread.setNotify(payResultNotifyReq, orderId);
		taskExecutor.execute(thread);
	}
	
	/**
	 * 签约结果通知
	 * @param entrustPayNotifyReq
	 */
	public void execute(EntrustPayNotifyReq entrustPayNotifyReq) {
		ContractNotifyThread thread = beanFactory.getBean(ContractNotifyThread.class);
		thread.setNotify(entrustPayNotifyReq);
		taskExecutor.execute(thread);
	}
	
	/**
     * 退款结果通知
     * @param payResultNotifyReq
     * @param tradeId
     */
    public void execute(RefundRsltNotifyReq refundRsltNotifyReq,OrderPO orderPO) {
        RefundRsltNotifyThread thread = beanFactory.getBean(RefundRsltNotifyThread.class);
        thread.setRefundRsltNotifyThread(refundRsltNotifyReq, orderPO);
        taskExecutor.execute(thread);
    }
	
	@PreDestroy
	private void destroy() {
		payResultNotifyQueue.stop();
		contractNotifyQueue.stop();
		refundRsltNotifyQueue.stop();
		taskExecutor.shutdown();	
		payResultNotifyTask.shutdown();
		contractNotifyTask.shutdown();		
		refundRsltNotifyTask.shutdown();
	}

	public BeanFactory getBeanFactory() {
		return beanFactory;
	}

	public void setBeanFactory(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}
}
