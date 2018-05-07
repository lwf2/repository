
package com.aspire.bpom.xml.bean.request;

/**
 * 订单模块申请退款接口请求    内部业务平台-->订单模块
 *
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "RefundReq")
@XStreamAlias("RefundReq")
public class RefundReq implements Serializable
{

    private static final long serialVersionUID = 3069926139750048033L;

    /*
     * 消息类型
     */
    private String MsgType;

    /*
     * 接口消息的版本号
     */
    private String Version;

    /*
     * 内部系统代码
     */
    private String systemCode;
    
    /*
     * 外部订单ID
     */
    private String orderId;
    /*
     * 外部交易ID
     */
    private String tradeId;   

    /*
     * 退款原因描述
     */
    private String refundDesc;

    /*
     * 数字签名:对以上所有字段按照定义的顺序取值再与网元密钥拼接相连，然后进行MD5摘要算法所得值，最后转换成16进制串（大写）。
     */
    private String hmac;

    public String getMsgType()
    {

        return MsgType;
    }

    public void setMsgType(String msgType)
    {

        MsgType = msgType;
    }

    public String getVersion()
    {

        return Version;
    }

    public void setVersion(String version)
    {

        Version = version;
    }

    public String getSystemCode()
    {

        return systemCode;
    }

    public void setSystemCode(String systemCode)
    {

        this.systemCode = systemCode;
    }

    public String getOrderId()
    {

        return orderId;
    }

    public void setOrderId(String orderId)
    {

        this.orderId = orderId;
    }
    
    public String getTradeId()
    {
    
        return tradeId;
    }

    
    public void setTradeId(String tradeId)
    {
    
        this.tradeId = tradeId;
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

        return "RefundReq [MsgType=" + MsgType + ", Version=" + Version
               + ", systemCode=" + systemCode + ", orderId=" + orderId
               + ", tradeId=" + tradeId + ", refundDesc=" + refundDesc
               + ", hmac=" + hmac + "]";
    }


    
    
 

}
