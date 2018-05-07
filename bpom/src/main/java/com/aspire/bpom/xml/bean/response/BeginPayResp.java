package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付并签约接口响应    支付网关-->订单系统
 * @author chenpeng
 *
 */
@XmlRootElement(name = "msgResp")
@XStreamAlias("msgResp")
public class BeginPayResp implements Serializable{

	private static final long serialVersionUID = 2450774902272800405L;

	private String msgType;
	private String msgVer;
	private Integer returnCode;
	private String returnMsg;
	private String requestId;
	private String orderId;
	private PayPrmts payPrmts;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public PayPrmts getPayPrmts() {
		return payPrmts;
	}
	public void setPayPrmts(PayPrmts payPrmts) {
		this.payPrmts = payPrmts;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	@Override
	public String toString() {
		return "BeginPayResp [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
				+ ", requestId=" + requestId + ", payPrmts=" + payPrmts + "]";
	}
	
}
