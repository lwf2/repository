package com.aspire.bpom.service;

import java.util.Map;

import com.aspire.bpom.bean.OrderPO;
import com.aspire.bpom.extensions.exception.BusinessException;
import com.aspire.bpom.xml.bean.request.RefundReq;
import com.aspire.bpom.xml.bean.request.RefundRsltNotifyReq;
import com.aspire.bpom.xml.bean.response.RefundNotifyResp;
import com.aspire.bpom.xml.bean.response.RefundResp;


public interface TUnsubscribeService
{
    /*
     * 修改退款单状态
     */
    public void updateStatus(OrderPO orderPO);
    
    /*
     * 保存退款单信息流水
     */
    public void insertRefundHis(OrderPO orderPO);
    /*
     * 申请退款接口
     */
    public Map<String,Object> refundService(RefundReq refundReq,RefundResp refundResp) throws BusinessException;
    /*
     * 退款结果通知接口
     */
    public RefundNotifyResp refundRsltNotifyService(RefundRsltNotifyReq refundRsltNotifyReq,OrderPO orderPO) throws BusinessException;
}
