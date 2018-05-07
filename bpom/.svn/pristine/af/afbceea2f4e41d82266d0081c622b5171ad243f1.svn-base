package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付结果通知接口    订单模块-->内部业务平台
 * @author chenpeng
 *
 */
@XmlRootElement(name = "PayOrderResultNotifyReq")
@XStreamAlias("PayOrderResultNotifyReq")
public class PayOrderResultNotifyReq implements Serializable{

	private static final long serialVersionUID = 7673692179322907834L;

	private String MsgType;
	
	private String Version;
	
	private String orderId;
	
	private String tradeId;
	
	private String payResult;
	
	private String amount;
	
	private String payOrganization;
	
	private String payTime;
	
	private String reserved1 ;
	
	private String payWay;
	
	private String payType;

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

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
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

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PayOrderResultNotifyReq [MsgType=" + MsgType + ", Version="
				+ Version + ", orderId=" + orderId + ", tradeId=" + tradeId
				+ ", payResult=" + payResult + ", amount=" + amount
				+ ", payOrganization=" + payOrganization + ", payTime="
				+ payTime + ", reserved1=" + reserved1 + ", payWay=" + payWay
				+ ", payType=" + payType + "]";
	}

}
