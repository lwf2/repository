package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信委托代扣解约申请
 * 支付网关-MM门户/计费网关
 * @author liuweifeng
 */
@XmlRootElement(name = "msgResp")
@XStreamAlias("msgResp")
public class CancelEntrustPayResp implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 消息类型  填写“CancelEntrustPayResp” */
	private String msgType;
	/** 该接口消息的版本号，本次所有的接口消息的版本都为“1.0”  */
	private String msgVer;
	/** 返回码   0表示处理成功，1表示处理失败  */
	private Integer returnCode;
	/** returnCode取值为1时有效,返回错误信息  */
	private String returnMsg;
	
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgVer() {
		return msgVer;
	}
	public void setMsgVer(String msgVer) {
		this.msgVer = msgVer;
	}
	public Integer getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CancelEntrustPayResp [msgType=" + msgType + ", msgVer="
				+ msgVer + ", returnCode=" + returnCode + ", returnMsg="
				+ returnMsg + "]";
	}
}
