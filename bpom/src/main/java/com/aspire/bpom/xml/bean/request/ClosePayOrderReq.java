package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 关闭订单接口请求    订单模块-->支付网关
 * @author chenpeng
 *
 */
@XmlRootElement(name = "msgReq")
@XStreamAlias("msgReq")
public class ClosePayOrderReq implements Serializable{

	private static final long serialVersionUID = 1L;

	private String msgType;
	
	private String msgVer;
	
	private String systemCode;
	
	private String requestId;
	
	private String orderId;

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

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "ClosePayOrderReq [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", systemCode=" + systemCode + ", requestId=" + requestId
				+ ", orderId=" + orderId + "]";
	}
	
}
