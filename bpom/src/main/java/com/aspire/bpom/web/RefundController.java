package com.aspire.bpom.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.ReturnCode;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.extensions.log4j.ThreadLocalTransactionID;
import com.aspire.bpom.service.TUnsubscribeService;
import com.aspire.bpom.util.MD5Util;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.RefundReq;
import com.aspire.bpom.xml.bean.response.RefundResp;
import com.aspire.bpom.xml.bean.response.RefundResponse;
import com.aspire.bpom.xml.common.ParseXMLHelper;

import org.apache.commons.lang.StringUtils;

@Controller
public class RefundController
{
    private static final Logger logger = BpomLogger.getLogger(RefundController.class);   
    
    private static ThreadLocal<String> dubugInfo = new ThreadLocal<String>();
    
    @Resource
    private SystemManager systemManager;
    
    @Resource
    private TUnsubscribeService  tUnsubscribeService;
    
    /**
     * 订单模块申请退款接口
     * @param xml
     * @return
     * @throws BusinessException
     * @throws Exception
     */
    @SuppressWarnings("finally")
    @RequestMapping("/weixin/refund")
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public String refund(@RequestBody String xml) throws BusinessException {
        String returnXml = null;
        Map<String,Object> map=null;
        String outOrderId=null;
        String tradeId=null;
        try{
          //将任我看平台请求参数报文转换为实体
            RefundReq refundReq = ParseXMLHelper.parseXMLToObject(xml, RefundReq.class);
            String sessionId = refundReq.getTradeId();
    		String logSession = ThreadLocalTransactionID.generateTransactionID(sessionId);
    		ThreadLocalTransactionID.setTransactionID(logSession);
            
            RefundResp refundResp=new RefundResp();
            logger.info("收到内部业务平台请求的消息报文为", xml);
            if(!verifyOtherParam(refundReq)){
                refundResp.setMsgType("RefundResp");
                refundResp.setVersion("1.0.0");
                refundResp.setReturnCode(String.valueOf(ErrorReturnCode.ERROR_REQUEST_PARAM));
                refundResp.setReturnMsg("请求参数校验错误:"+dubugInfo.get());
                returnXml= ParseXMLHelper.object2XML(RefundResp.class, refundResp);
                logger.error("参数错误，返回报文：" + returnXml);
            }
            else
            {
                map=new HashMap<String,Object>();
                RefundResponse refundResponse=new RefundResponse();
                map=tUnsubscribeService.refundService(refundReq, refundResp);
                outOrderId=(String)map.get("outOrderId");
                tradeId=(String)map.get("tradeId");
                refundResponse=(RefundResponse)map.get("refundResponse");
              

                //组装计费平台返回到任我看平台响应参数报文
                if(ReturnCode.SUCCESS.equals(Integer.parseInt(refundResponse.getReturnCode()))){     
                    refundResp.setReturnMsg(refundResponse.getReturnMsg());
                    refundResp.setReturnCode(refundResponse.getReturnCode());
                    refundResp.setOrderId(outOrderId);
                    refundResp.setTradeId(tradeId);
                }else{
                    refundResp.setReturnMsg(refundResponse.getReturnMsg());
                    refundResp.setReturnCode(refundResponse.getReturnCode());
                }
                refundResp.setMsgType("RefundResp");
                refundResp.setVersion("1.0.0");
                returnXml = ParseXMLHelper.object2XML(RefundResp.class, refundResp);//转化为xml格式
            }
           
        } finally {
            logger.info("返回任我看平台的xml returnXml：" + returnXml);
            return returnXml; 
        }
        
    }

    //验证请求参数
    public boolean verifyOtherParam(RefundReq refundReq) throws InstantiationException, IllegalAccessException{
      //1.校验请求报文合法性。必填字段
        if(StringUtils.isEmpty(refundReq.getMsgType())) {
            logger.error("消息类型不能为空"); 
            dubugInfo.set("消息类型不能为空");
            return false;
        }else if(!"RefundReq".equals(refundReq.getMsgType()))
        {
            logger.error("消息类型不为RefundReq");
            dubugInfo.set("消息类型不为RefundReq");
            return false;
        }else if(StringUtils.isEmpty(refundReq.getVersion())) {
            
            logger.error("Version 版本号不能为空");
            dubugInfo.set("Version 版本号不能为空");
            return false;
        }else if(!"1.0.0".equals(refundReq.getVersion()))
        {
            logger.error("Version 版本号不为RefundReq");
            dubugInfo.set("Version 版本号不为RefundReq");
            return false;
        }else if(StringUtils.isEmpty(refundReq.getSystemCode())) {
            logger.error("内部系统代码不能为空");
            dubugInfo.set("内部系统代码不能为空");
            return false;
        }
        if(StringUtils.isEmpty(refundReq.getOrderId())) {
            logger.error("外部订单 id不能为空");
            dubugInfo.set("外部订单 id不能为空");
            return false;
        }else if(StringUtils.isEmpty(refundReq.getTradeId())) {
            logger.error("外部交易ID不能为空");
            dubugInfo.set("外部交易ID不能为空");
            return false;
       }else if(StringUtils.isEmpty(refundReq.getRefundDesc())) {
            logger.error("退款原因描述不能为空");
            dubugInfo.set("退款原因描述不能为空");
            return false;
        }
        if(StringUtils.isEmpty(refundReq.getHmac())) {
            logger.error("数字签名不能为空");
            dubugInfo.set("数字签名不能为空");
            return false;
        }
        else
        {
        	//生成本地签名校验
			String _hmac = MD5Util.getCode(MD5Util.getEntityStr(refundReq) + systemManager.getSecretKeyRWK());
			logger.info("任我看请求的签名：" + refundReq.getHmac());
			logger.info("本地生成签名：" + _hmac);
			//签名校验
			if(!_hmac.equals(refundReq.getHmac())){
			  logger.error("校验签名错误");
	          dubugInfo.set("校验签名错误 ");
              return false;
             }
        }
        return true;
        
        
    }
    
}
