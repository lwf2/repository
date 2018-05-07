package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/** 
 * 订单模块申请退款接口请求    订单模块-->支付网关
*
*/

@XmlRootElement(name = "msgReq")
@XStreamAlias("msgReq")
public class RefundRequest implements Serializable
{

    
    private static final long serialVersionUID = 482738657785406981L;
    

    /*
     * 消息类型
     */
    private String msgType;

    /*
     * 接口消息的版本号
     */
    private String msgVer;

    /*
     *商户请求交易流水号
     */
    private String requestId;
    /*
     *用户ID
     */
    private String userId;
    /*
     * 订单ID
     */
    private String orderId;
    /*
     *退款金额，以分为单位，与原订单的金额相等
     */
    private Integer refundAmount;

    /*
     * 退款原因描述
     */
    private String refundDesc;
    /*
     * 内部系统代码
     */
    private String systemCode;

    /*
     * 数字签名:对以上所有字段按照定义的顺序取值再与网元密钥拼接相连，然后进行MD5摘要算法所得值，最后转换成16进制串（大写）。
     */
    private String hmac;

    
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

    
    public String getSystemCode()
    {
    
        return systemCode;
    }

    
    public void setSystemCode(String systemCode)
    {
    
        this.systemCode = systemCode;
    }

    
    public String getRequestId()
    {
    
        return requestId;
    }

    
    public void setRequestId(String requestId)
    {
    
        this.requestId = requestId;
    }

    
    public String getUserId()
    {
    
        return userId;
    }

    
    public void setUserId(String userId)
    {
    
        this.userId = userId;
    }

     
    public Integer getRefundAmount()
    {
    
        return refundAmount;
    }


    
    public void setRefundAmount(Integer refundAmount)
    {
    
        this.refundAmount = refundAmount;
    }


    public String getOrderId()
    {
    
        return orderId;
    }

    
    public void setOrderId(String orderId)
    {
    
        this.orderId = orderId;
    }

    
    public String getRefundDesc()
    {
    
        return refundDesc;
    }

    
    public void setRefundDesc(String refundDesc)
    {
    
        this.refundDesc = refundDesc;
    }

    
    public String getHmac()
    {
    
        return hmac;
    }

    
    public void setHmac(String hmac)
    {
    
        this.hmac = hmac;
    }


    @Override
    public String toString()
    {

        return "RefundRequest [msgType=" + msgType + ", msgVer=" + msgVer
               + ", systemCode=" + systemCode + ", requestId=" + requestId
               + ", userId=" + userId + ", refundAmount=" + refundAmount
               + ", orderId=" + orderId + ", refundDesc=" + refundDesc
               + ", hmac=" + hmac + "]";
    }
    
    

    

}
