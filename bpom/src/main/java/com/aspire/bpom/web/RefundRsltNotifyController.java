package com.aspire.bpom.web;


import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.PayStatus;
import com.aspire.bpom.constants.RefundResultStatus;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.service.OrderService;
import com.aspire.bpom.service.TUnsubscribeService;
import com.aspire.bpom.thread.NotifyThreadPool;
import com.aspire.bpom.xml.bean.request.RefundRsltNotifyReq;
import com.aspire.bpom.xml.bean.response.RefundRsltNotifyResp;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Controller
public class RefundRsltNotifyController
{
    private static final Logger logger = BpomLogger.getLogger(RefundRsltNotifyController.class);
    
    
    private static ThreadLocal<String> dubugInfo = new ThreadLocal<String>();
    
    @Resource
    private OrderService orderService;
    
    @Resource
    private TUnsubscribeService  tUnsubscribeService;
    
    @Resource
    private NotifyThreadPool notifyThreadPool;
    
    /**
     * 订单模块退款结果通知接口
     * @param xml
     * @return
     * @throws BusinessException
     * @throws Exception
     */
    @SuppressWarnings("finally")
    @RequestMapping("/weixin/refundRsltNotify")
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String refundRsltNotify(@RequestBody String xml) {
    	
    	RefundRsltNotifyReq refundRsltNotifyReq = ParseXMLHelper.parseXMLToObject(xml, RefundRsltNotifyReq.class);
    	 String sessionId = refundRsltNotifyReq.getOrderId();
 		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
 		ThreadLocalTransactionID.setTransactionID(logSession);
 		logger.info("收到支付网关请求的消息报文为" + xml);
    	
    	String returnXml = null;
    	RefundRsltNotifyResp refundRsltNotifyResp = new RefundRsltNotifyResp();
    	try{
    		//将支付网关请求参数报文转换为实体
    		if(!verifyOtherParam(refundRsltNotifyReq)) {
    			//请求参数校验不正确
    			refundRsltNotifyResp.setReturnCode(ErrorReturnCode.ERROR_REQUEST_PARAM);
    			refundRsltNotifyResp.setReturnMsg(dubugInfo.get());
    		} else {
    			OrderPO orderPO=orderService.getOrderById( refundRsltNotifyReq.getRequestId(),refundRsltNotifyReq.getOrderId());
    			if(orderPO==null) {
    				logger.error("查询不到订单信息！");
    				refundRsltNotifyResp.setReturnCode(ReturnCode.FAIL);
    				refundRsltNotifyResp.setReturnMsg("查询不到订单信息！");
    			} else {
    				if(RefundResultStatus.REFUND_SUCCESS.equals(refundRsltNotifyReq.getRefundStatus())) {
    					//更新退款单状态为退款成功状态
    					orderPO.setPayStatus(PayStatus.PAY_STATUS_70);
    				} else if(RefundResultStatus.FILL_BILL_REFUND_SUCCESS.equals(refundRsltNotifyReq.getRefundStatus())) {
    					//更新退款单状态为退款补单成功状态
    					orderPO.setPayStatus(PayStatus.PAY_STATUS_90);
    				} else{
    					//更新退款单状态为退款失败状态
    					orderPO.setPayStatus(PayStatus.PAY_STATUS_80);
    				}
    				logger.info("退款单状态更新为："+orderPO.getPayStatus());
    				tUnsubscribeService.updateStatus(orderPO);

    				logger.info("记录退款单信息流水");
    				tUnsubscribeService.insertRefundHis(orderPO);
    				refundRsltNotifyReq.setTimingNumber(0);//设置重发通知的次数
    				refundRsltNotifyReq.setSessionId(logSession);//为线程设置日志记号
    				notifyThreadPool.execute(refundRsltNotifyReq, orderPO);
    				logger.info("发送通知成功，启动延时通知线程");
    				refundRsltNotifyResp.setReturnCode(ReturnCode.SUCCESS);
    				refundRsltNotifyResp.setReturnMsg("");
    			}

    		}

    	}  catch (Exception e) {
    		logger.error("退款结果通知处理异常");
    		refundRsltNotifyResp.setReturnCode(ErrorReturnCode.EXCEPTION);
    		refundRsltNotifyResp.setReturnMsg("退款结果通知处理异常");
    	} finally {
    		refundRsltNotifyResp.setMsgType("RefundRsltNotifyResp");
    		refundRsltNotifyResp.setMsgVer("1.0");
    		returnXml = ParseXMLHelper.object2XML(RefundRsltNotifyResp.class, refundRsltNotifyResp);//转化为xml格式

    		logger.info("返回给支付网关的xml returnXml：" + returnXml);
    		return returnXml; 
    	}

    }
    
    //验证请求参数
    public boolean verifyOtherParam(RefundRsltNotifyReq refundNotifyReq){
      //1.校验请求报文合法性。必填字段
        if(!"RefundRsltNotifyReq".equals(refundNotifyReq.getMsgType())) {
            dubugInfo.set("消息类型不为RefundRsltNotifyReq");
            return false;
        } else if(!"1.0".equals(refundNotifyReq.getMsgVer())) {
            dubugInfo.set("Version 版本号不为1.0");
            return false; 
        }else if(StringUtils.isEmpty(refundNotifyReq.getOrderId())) {
            dubugInfo.set("orderId不能为空");
            return false;
        } else if(StringUtils.isEmpty(refundNotifyReq.getRequestId())) {
            dubugInfo.set("商户请求交易流水号requestid不能为空");
            return false;
        }else  if(refundNotifyReq.getRefundStatus() == null) {
            dubugInfo.set("退款结果状态不能为空");
            return false;
        }
        
        return true;
    }

}
