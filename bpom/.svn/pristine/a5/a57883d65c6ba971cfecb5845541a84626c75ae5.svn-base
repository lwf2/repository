package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 订单模块申请退款接口响应    支付网关-->订单模块
 *
 */
@XmlRootElement(name = "msgResp")
@XStreamAlias("msgResp")
public class RefundResponse implements Serializable
{
    private static final long serialVersionUID = -6982180409381611290L;
    /*
     * 消息类型
     */
    private String msgType;

    /*
     * 接口消息的版本号
     */
    private String msgVer;

    /*
     * 0：成功 1：失败
     */
    private String returnCode;

    /*
     * 返回错误信息
     */
    private String returnMsg;

    
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


    @Override
    public String toString()
    {

        return "RefundResponse [msgType=" + msgType + ", msgVer=" + msgVer
               + ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
               + "]";
    }
    
    
}
