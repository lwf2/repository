package com.aspire.bpom.task;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.OrderCountService;

/**
 * 生成订单结算定时任务
 * @author chenpeng
 *
 */
@Component
public class OrderCountTask {

	private static final Logger logger = BpomLogger.getLogger(OrderCountTask.class);

	
	@Resource
	private OrderCountService orderCountService;
	
    @Scheduled(cron = "${youshuCron}")
    public void work() {

    	logger.info("定时任务创建结算文件开始。。");
    	//orderCountService.createCountFile();
    	try
        {
            orderCountService.consume();
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       
    }
}
