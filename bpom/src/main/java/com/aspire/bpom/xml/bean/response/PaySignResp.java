package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付并签约接口响应    订单模块-->内部业务平台
 * @author chenpeng
 *
 */
@XmlRootElement(name = "PaySignResp")
@XStreamAlias("PaySignResp")
public class PaySignResp implements Serializable{

	private static final long serialVersionUID = -5475080085414872037L;

	private String MsgType;
	private String Version;
	private String orderId;
	private String orderTime;
	private String tradeId;
	private Integer returnCode;
	private String returnMsg;
	private PayPrmts payPrmts;
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getVersion() {
		return Version;
	}
	public void setVersion(String version) {
		Version = version;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
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
	public PayPrmts getPayPrmts() {
		return payPrmts;
	}
	public void setPayPrmts(PayPrmts payPrmts) {
		this.payPrmts = payPrmts;
	}
	public String getTradeId() {
		return tradeId;
	}
	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}
	@Override
	public String toString() {
		return "PaySignResp [MsgType=" + MsgType + ", Version=" + Version
				+ ", orderId=" + orderId + ", orderTime=" + orderTime
				+ ", tradeId=" + tradeId + ", returnCode=" + returnCode
				+ ", returnMsg=" + returnMsg + ", payPrmts=" + payPrmts + "]";
	}
}
