package com.aspire.bpom.service.impl;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.constants.ErrorReturnCode;
import com.aspire.bpom.constants.PayStatus;
import com.aspire.bpom.constants.RWKRefundResultStatus;
import com.aspire.bpom.constants.RefundResultStatus;
import com.aspire.bpom.dao.OrderDao;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.extensions.log4j.BpomLogger;
import com.aspire.bpom.service.TUnsubscribeService;
import com.aspire.bpom.util.DateUtil;
import com.aspire.bpom.util.HttpClientUtil;
import com.aspire.bpom.util.MD5Util;
import com.aspire.bpom.util.SystemManager;
import com.aspire.bpom.xml.bean.request.RefundNotifyReq;
import com.aspire.bpom.xml.bean.request.RefundReq;
import com.aspire.bpom.xml.bean.request.RefundRequest;
import com.aspire.bpom.xml.bean.request.RefundRsltNotifyReq;
import com.aspire.bpom.xml.bean.response.RefundNotifyResp;
import com.aspire.bpom.xml.bean.response.RefundResp;
import com.aspire.bpom.xml.bean.response.RefundResponse;
import com.aspire.bpom.xml.common.ParseXMLHelper;

@Service
public class TUnsubscribeServiceImpl implements TUnsubscribeService
{
    private static final Logger logger = BpomLogger.getLogger(TUnsubscribeServiceImpl.class);
    
    @Resource
    private OrderDao orderDao;
    
    @Resource
    private SystemManager sm;
    
    /*
     * 修改退款单状态
     */
    @Override
    public void updateStatus(OrderPO orderPO)
    {
        try
        {
            orderDao.updateRefundStatus(orderPO);
        }
        catch (Exception e)
        {
            logger.error("更新退款单状态失败！",e);
        }

        
    }
    /*
     * 保存退款单信息流水
     */
    @Override
    public void insertRefundHis(OrderPO orderPO)
    {
        try
        {
            orderDao.inserOrderHis(orderPO);
        }
        catch (Exception e)
        {
            logger.error("保存退款单信息流水失败！",e);
        }

        
    }
    /**
     * 退款申请接口
     * @param refundReq
     * @param refundResp
     * @return
     * @throws BusinessException 
     */
    @Override
    public Map<String,Object> refundService(RefundReq refundReq,RefundResp refundResp) throws BusinessException {  
        Map<String,Object> resultMap=new HashMap<String, Object>();
        RefundRequest refundRequest =new RefundRequest ();
        RefundResponse refundResponse=new RefundResponse();
        //组装调用支付网关的参数。
        resultMap =  getRefundRequest(refundReq, refundResp); 
        refundRequest=(RefundRequest)resultMap.get("refundRequest");
        logger.info("请求支付网关的参数为：" + refundRequest.toString());
        //组装报文请求支付网关
        refundResponse= postRefundResponse(refundRequest, refundResp);
        resultMap.put("refundResponse", refundResponse);
        return resultMap;
    }
    
    /**
     * 退款申请接口组装调用支付网关的参数
     * @param refundReq
     * @param refundResp
     * @return
     * @throws BusinessException 
     */
    private Map<String,Object> getRefundRequest(RefundReq refundReq,RefundResp refundResp) throws BusinessException{
        RefundRequest refundRequest =new RefundRequest ();
        logger.error("任我看发送请求参数到计费平台"+refundReq);
        String outOrderId=null;
        String tradeId=null;
        Integer count=null;
        Map<String,Object> resultMap=null;
        try {
            OrderPO tOrder=new OrderPO();
            tOrder=orderDao.getOrderById(null,refundReq.getTradeId());
            logger.info("根据外部交易ID查询订单信息"+refundReq.getTradeId());
            outOrderId=refundReq.getOrderId();
            tradeId=refundReq.getTradeId();
            tOrder.setOutOrderId(outOrderId);
            tOrder.setTradeId(tradeId);
            tOrder.setRefundDesc(refundReq.getRefundDesc());
            tOrder.setPayStatus(PayStatus.PAY_STATUS_60);//60：退款中                       
            count=orderDao.insertRefund(tOrder);
            orderDao.inserOrderHis(tOrder);  
                if(count>0)
                {
                    refundRequest=postGetRefundRequest(refundReq,tOrder);
                    logger.info("添加退款单信息成功"+tOrder);
                } 
                
            resultMap=new HashMap<String, Object>();
            resultMap.put("refundRequest", refundRequest);
            resultMap.put("outOrderId", outOrderId);
            resultMap.put("tradeId", tradeId);
        } catch (Exception e) {
            refundResp.setReturnCode(String.valueOf(ErrorReturnCode.ERROR_INTERNAL_DB));
            refundResp.setReturnMsg("插入方法tUnsubscribeDao.queryCount(refundReq.getTradeId())处理异常");
            logger.error("TUnsubscribeServiceImpl.getRefundRequest().error 添加退款单信息失败：" + e);
            throw new BusinessException("TUnsubscribeServiceImpl.getRefundRequest()().error 添加退款单信息失败", e);
        }
        return resultMap;
    }
    /**
     * 退款申请接口请求支付网关
     * @param refundRequest
     * @param qryContractResp
     * @return
     * @throws BusinessException
     */
    private RefundResponse postRefundResponse(RefundRequest refundRequest,
                                                    RefundResp refundResp) throws BusinessException{
        RefundResponse refundResponse = null;
        try {
          //3.组装报文请求支付网关
            String subReqXml = ParseXMLHelper.object2XML(RefundRequest.class, refundRequest);
            //读取配置文件
            String subRespXml = HttpClientUtil.doHttpResult(sm.getRefundreqURL()
                                                            , subReqXml);
            //将支付网关返回的响应参数转换为实体
            refundResponse = ParseXMLHelper.parseXMLToObject(subRespXml, RefundResponse.class);
        } catch (Exception e) {
            refundResp.setReturnCode(String.valueOf(ErrorReturnCode.ERROR_REQUEST_PAYMENT_GATEWAY));
            refundResp.setReturnMsg("支付网关响应异常");
            logger.error("TUnsubscribeServiceImpl.postRefundResponse().error 支付网关响应异常" + e);
            throw new BusinessException("TUnsubscribeServiceImpl.postRefundResponse().error 支付网关响应异常", e);
        }
        logger.info("支付网关响应内容为："+refundResponse);
        return refundResponse;
    } 
    
    /**
     * 退款申请接口组装调用支付网关的参数
     * @param refundReq
     * @param tOrder
     * @return
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws BusinessException 
     */
   public RefundRequest postGetRefundRequest(RefundReq refundReq,OrderPO tOrder) throws InstantiationException, IllegalAccessException{
       RefundRequest refundRequest=new RefundRequest();
       String hmac=null;
       refundRequest.setMsgType("RefundReq");
       refundRequest.setMsgVer("1.0");
       refundRequest.setOrderId(refundReq.getOrderId());
       refundRequest.setSystemCode(refundReq.getSystemCode());
       refundRequest.setRefundAmount(tOrder.getAmount());
       refundRequest.setRequestId(tOrder.getRequestId());
       refundRequest.setUserId(tOrder.getUserId());
       refundRequest.setRefundDesc(refundReq.getRefundDesc());
       hmac=MD5Util.getCode(MD5Util.getEntityStr(refundRequest) + sm.getSecretKeyBPOM());
       logger.info("申请退款接口请求支付网关数字签名为："+hmac);
       refundRequest.setHmac(hmac);
       return refundRequest;
   }
   /**
    * 退款结果通知接口
    * @param refundRsltNotifyReq
    * @param tOrder,refundRsltNotifyResp
    * @return RefundNotifyReq
    * @throws BusinessException 
    */
   public RefundNotifyResp refundRsltNotifyService(RefundRsltNotifyReq refundRsltNotifyReq,OrderPO tOrder) throws BusinessException {
       //组装请求内部业务系统报文
       RefundNotifyReq refundNotifyReq= setNewRefundRsltNotifyReq(refundRsltNotifyReq,tOrder);
       logger.info("构造请求内部业务系统的参数为："+refundNotifyReq.toString());
       RefundNotifyResp refundNotifyResp =  postRefundNotifyResp(refundNotifyReq);
       
       return refundNotifyResp;
   }
   
   /**
    * 请求业务平台退款结果通知接口
    * @param refundNotifyReq
    * @return RefundNotifyResp
    */
   private RefundNotifyResp postRefundNotifyResp(RefundNotifyReq refundNotifyReq){
       //3.组装报文请求内部平台
       String subReqXml = ParseXMLHelper.object2XML(RefundNotifyReq.class, refundNotifyReq);
       RefundNotifyResp refundNotifyResp = null;
       try{
           String subRespXml = HttpClientUtil.doHttpResult(sm.getRefundnotifyURL()
                                                           , subReqXml);//请求内部业务退款结果通知接口
           refundNotifyResp = ParseXMLHelper.parseXMLToObject(subRespXml, RefundNotifyResp.class);
       }catch(Exception e){
           logger.error("内部业务平台响应异常："+e);
           refundNotifyResp.setReturnCode(String.valueOf(ErrorReturnCode.ERROR_CONNECT_OUT_RWK));
           refundNotifyResp.setReturnMsg("计费平台同内部业务平台通信异常");;
       }
       logger.info("收到内部业务平台返回的响应-返回值为："+refundNotifyResp.getReturnCode());
       return refundNotifyResp;
   }
   
   /**
    * 订单模块退款结果通知接口组装调用内部业务平台的参数
    * @param refundRsltNotifyReq
    * @param tUnsubscribePO,refundRsltNotifyResp
    * @return RefundNotifyReq
    * @throws BusinessException 
    */
    private RefundNotifyReq setNewRefundRsltNotifyReq(RefundRsltNotifyReq refundRsltNotifyReq,OrderPO tOrder) throws BusinessException
    {
    	logger.error("支付网关参数到计费平台"+refundRsltNotifyReq);
    	RefundNotifyReq refundNotifyReq=new RefundNotifyReq();
    	refundNotifyReq.setMsgType("RefundNotifyReq");
    	refundNotifyReq.setVersion("1.0.0");
    	refundNotifyReq.setOrderId(tOrder.getOutOrderId());
    	refundNotifyReq.setTradeId(refundRsltNotifyReq.getOrderId());
    	refundNotifyReq.setRefundAmount(tOrder.getAmount());
    	if(RefundResultStatus.REFUND_SUCCESS.equals(refundRsltNotifyReq.getRefundStatus())||RefundResultStatus.FILL_BILL_REFUND_SUCCESS.equals(refundRsltNotifyReq.getRefundStatus())){              
    		refundNotifyReq.setRefundStatus(RWKRefundResultStatus.REFUND_SUCCESS);
    	} else {
    		refundNotifyReq.setRefundStatus(RWKRefundResultStatus.REFUND_FAILED);
    	}
    	try {
    		refundNotifyReq.setRefundDate(DateUtil.getForTimeStamp(DateUtil.getDate(tOrder.getOperateTime())));
    	} catch (ParseException e) {
    		logger.error("日期转化异常");
    	}
    	return refundNotifyReq;
    }


}
