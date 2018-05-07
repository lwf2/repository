package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 订单模块退款结果通知接口响应  内部业务平台-->支付网关
 * 
 */
@XmlRootElement(name = "msgResp")
@XStreamAlias("msgResp")
public class RefundRsltNotifyResp implements Serializable
{
    private static final long serialVersionUID = 1L;
    
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
    private Integer returnCode;

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
    
    public Integer getReturnCode() {
		return returnCode;
	}


	public void setReturnCode(Integer returnCode) {
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

        return "RefundRsltNotifyResp [msgType=" + msgType + ", msgVer="
               + msgVer + ", returnCode=" + returnCode + ", returnMsg="
               + returnMsg + "]";
    }
    
    


    

}
