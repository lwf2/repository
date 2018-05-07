package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付结果查询响应。 支付网关--》订单模块
 * @author chenpeng
 *
 */
@XmlRootElement(name = "msgResp")
@XStreamAlias("msgResp")
public class QueryPayStatusResp implements Serializable{

	private static final long serialVersionUID = 1L;

	private String msgType;
	
	private String msgVer;
	
	private Integer returnCode;
	
	private String returnMsg;
	
	private String orderId;
	
	private String requestId;
	
	private Integer payStatus;
	
	private Integer amount ;
	
	private String payDate ;
	
	private String refundAmount;
	
	private String refundDate;
	
	private String refundStatus;

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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public String getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(String refundStatus) {
		this.refundStatus = refundStatus;
	}

	@Override
	public String toString() {
		return "QueryPayStatusResp [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
				+ ", orderId=" + orderId + ", requestId=" + requestId
				+ ", payStatus=" + payStatus + ", amount=" + amount
				+ ", payDate=" + payDate + ", refundAmount=" + refundAmount
				+ ", refundDate=" + refundDate + ", refundStatus="
				+ refundStatus + "]";
	}
	
}
