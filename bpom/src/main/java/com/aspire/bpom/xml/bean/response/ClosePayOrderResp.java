package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 关闭订单接口响应 支付网关--》订单模块
 * @author chenpeng
 *
 */
@XmlRootElement(name = "msgResp")
@XStreamAlias("msgResp")
public class ClosePayOrderResp implements Serializable{

	private static final long serialVersionUID = 1L;

	private String msgType;
	
	private String msgVer;
	
	private Integer returnCode;
	
	private String returnMsg;
	
	private String requestId;

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
	
	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "ClosePayOrderResp [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
				+ ", requestId=" + requestId + "]";
	}

}
