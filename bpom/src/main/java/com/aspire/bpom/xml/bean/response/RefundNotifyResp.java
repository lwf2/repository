
package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 订单模块退款结果通知接口响应 内部业务平台-->订单模块
 * 
 */
@XmlRootElement(name = "RefundNotifyResp")
@XStreamAlias("RefundNotifyResp")
public class RefundNotifyResp implements Serializable
{

    private static final long serialVersionUID = -2708260870148479658L;

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

    @Override
    public String toString()
    {

        return "RefundNotifyResp [MsgType=" + MsgType + ", Version=" + Version
               + ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
               + "]";
    }

}
