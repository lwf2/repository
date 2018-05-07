package com.aspire.bpom.xml.bean.response;

/**
 * 订单对账
 * 计费平台-任我看平台
 * @author liuweifeng
 *
 */
public class OrderReconResp {

	/** 业务平台ID */
	private String servPltfmCode;
	
	/** 订单号 */
	private String orderId;
	
	/** 交易类型 */
	private String transType;
	
	/** 外部交易ID */
	private String tradeId;
	
	/** 提交支付时间 */
	private String submitTime;
	
	/** 第三方支付机构代码 */
	private String payOrganization;
	
	/** 支付通道代码 */
	private String payWap;
	
	/** 支付方式代码 */
	private String payType;
	
	/** 交易金额 */
	private String totalFee;
	
	/** 交易完成时间 */
	private String payTime;

	public String getServPltfmCode() {
		return servPltfmCode;
	}

	public void setServPltfmCode(String servPltfmCode) {
		this.servPltfmCode = servPltfmCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(String submitTime) {
		this.submitTime = submitTime;
	}

	public String getPayOrganization() {
		return payOrganization;
	}

	public void setPayOrganization(String payOrganization) {
		this.payOrganization = payOrganization;
	}

	public String getPayWap() {
		return payWap;
	}

	public void setPayWap(String payWap) {
		this.payWap = payWap;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	@Override
	public String toString() {
		return "OrderReconResp [servPltfmCode=" + servPltfmCode + ", orderId="
				+ orderId + ", transType=" + transType + ", tradeId=" + tradeId
				+ ", submitTime=" + submitTime + ", payOrganization="
				+ payOrganization + ", payWap=" + payWap + ", payType="
				+ payType + ", totalFee=" + totalFee + ", payTime=" + payTime
				+ "]";
	}
}
