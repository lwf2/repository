package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付结果查询响应。 订单模块--》内部业务平台
 * @author chenpeng
 *
 */
@XmlRootElement(name = "QryPayResp")
@XStreamAlias("QryPayResp")
public class QryPayResp implements Serializable{

	private static final long serialVersionUID = 1L;

	private String MsgType;
	
	private String Version;
	
	private Integer returnCode;
	
	private String returnMsg;
	
	private String orderId;
	
	private String tradeId;
	
	private Integer payStatus;
	
	private Integer amount ;
	
	private String payTime;
	
	private Integer refundAmount;
	
	private String refundDate;
	
	private Integer refundStatus;

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

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Integer getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Integer refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(String refundDate) {
		this.refundDate = refundDate;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(Integer refundStatus) {
		this.refundStatus = refundStatus;
	}
	
}
