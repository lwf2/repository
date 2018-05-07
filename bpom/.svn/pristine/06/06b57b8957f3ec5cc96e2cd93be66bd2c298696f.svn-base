package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付结果响应 支付网关--》订单模块
 * @author chenpeng
 *
 */
@XmlRootElement(name = "msgResp")
@XStreamAlias("msgResp")
public class PayResultNotifyResp implements Serializable{

	private static final long serialVersionUID = 7398709530155917322L;

	private String msgType;
	
	private String msgVer;
	
	private Integer	returnCode ;
	
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
		return "PayResultNotifyResp [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
				+ "]";
	}
	
}
