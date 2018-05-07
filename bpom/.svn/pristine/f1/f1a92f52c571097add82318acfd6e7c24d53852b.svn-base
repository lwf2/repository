package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.aspire.bpom.bean.TimeNotifyPO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 订单模块退款结果通知接口请求   订单模块-->支付网关
 * 
 */
@XmlRootElement(name = "msgReq")
@XStreamAlias("msgReq")
public class RefundRsltNotifyReq extends TimeNotifyPO implements Serializable 
{

    private static final long serialVersionUID = -2008459150966880637L;
    
    /*
     * 消息类型
     */
    private String msgType;

    /*
     * 接口消息的版本号
     */
    private String msgVer;

    /*
     * 订单ID
     */
    private String orderId;

    /*
     * 商户请求交易流水号
     */
    private String requestId;

    /*
     *实际退款金额，以分为单位
     */
    private Integer refundAmount;


    /*
     * 退款结果状态，取值：
     *0：表示成功
     *1：表示失败
     *2：对账退款成功
     */
    private Integer refundStatus;


    
    public String getMsgType()
    {
    
        return msgType;
    }


    
    public void setMsgType(String msgType)
    {
    
        this.msgType = msgType;
    }


    
    public String getMsgVer()
    {
    
        return msgVer;
    }


    
    public void setMsgVer(String msgVer)
    {
    
        this.msgVer = msgVer;
    }


    
    public String getOrderId()
    {
    
        return orderId;
    }


    
    public void setOrderId(String orderId)
    {
    
        this.orderId = orderId;
    }


    
    public String getRequestId()
    {
    
        return requestId;
    }


    
    public void setRequestId(String requestId)
    {
    
        this.requestId = requestId;
    }


    
    public Integer getRefundAmount()
    {
    
        return refundAmount;
    }


    
    public void setRefundAmount(Integer refundAmount)
    {
    
        this.refundAmount = refundAmount;
    }


    
    public Integer getRefundStatus()
    {
    
        return refundStatus;
    }


    
    public void setRefundStatus(Integer refundStatus)
    {
    
        this.refundStatus = refundStatus;
    }



    @Override
    public String toString()
    {

        return "RefundRsltNotifyReq [msgType=" + msgType + ", msgVer=" + msgVer
               + ", orderId=" + orderId + ", requestId=" + requestId
               + ", refundAmount=" + refundAmount + ", refundStatus="
               + refundStatus + "]";
    }
    
    
    
}
