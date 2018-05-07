
package com.aspire.bpom.xml.bean.response;

/**
 * 订单模块申请退款接口响应    订单模块-->内部业务平台
 *
 */

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XmlRootElement(name = "RefundResp")
@XStreamAlias("RefundResp")
public class RefundResp implements Serializable
{

    private static final long serialVersionUID = -5707069620989938868L;

    /*
     * 消息类型
     */
    private String MsgType;

    /*
     * 接口消息的版本号
     */
    private String Version;

    /*
     * 0：成功 1：失败
     */
    private String returnCode;

    /*
     * 返回错误信息
     */
    private String returnMsg;

    /*
     * 外部订单ID
     */
    private String orderId;
    /*
     * 外部交易ID
     */
    private String tradeId; 

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

    public String getReturnCode()
    {

        return returnCode;
    }

    public void setReturnCode(String returnCode)
    {

        this.returnCode = returnCode;
    }

    public String getReturnMsg()
    {

        return returnMsg;
    }

    public void setReturnMsg(String returnMsg)
    {

        this.returnMsg = returnMsg;
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

    @Override
    public String toString()
    {

        return "RefundResp [MsgType=" + MsgType + ", Version=" + Version
               + ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
               + ", orderId=" + orderId + ", tradeId=" + tradeId + "]";
    }
  
    

}
