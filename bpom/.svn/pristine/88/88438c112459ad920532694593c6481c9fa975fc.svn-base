package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.aspire.bpom.bean.TimeNotifyPO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付结果通知接口    支付网关-->订单模块
 * @author chenpeng
 *
 */
@XmlRootElement(name = "msgReq")
@XStreamAlias("msgReq")
public class PayResultNotifyReq extends TimeNotifyPO implements Serializable{

	private static final long serialVersionUID = 6365471380881421908L;

	private String msgType;
	
	private String msgVer;
	
	private String requestId;
	
	private String orderId;
	
	private String payResult;
	
	private String amount;
	
	private String payOrganization;
	
	private String payDate;
	
	private String reserved1 ;
	
	private String payWay;
	
	private String payType;

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

	public String getPayResult() {
		return payResult;
	}

	public void setPayResult(String payResult) {
		this.payResult = payResult;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPayOrganization() {
		return payOrganization;
	}

	public void setPayOrganization(String payOrganization) {
		this.payOrganization = payOrganization;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Override
	public String toString() {
		return "PayResultNotifyReq [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", requestId=" + requestId + ", orderId=" + orderId
				+ ", payResult=" + payResult + ", amount=" + amount
				+ ", payOrganization=" + payOrganization + ", payDate="
				+ payDate + ", reserved1=" + reserved1 + ", payWay=" + payWay
				+ ", payType=" + payType + "]";
	}
	
	
}
