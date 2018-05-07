package com.aspire.bpom.xml.bean.request;

/**
 * 支付交易对账单
 * 支付网关-计费平台
 * @author liuweifeng
 *
 */
public class PayTransReconReq {

	/** 业务平台ID */
	private String servPltfmCode;
	
	/** 业务系统订单号 */
	private String orderId;
	
	/** 交易类型 */
	private String transType;
	
	/** 支付网关交易ID */
	private String requestId;
	
	/** 提交支付时间 */
	private String submitTime;
	
	/** 第三方支付机构代码 */
	private String payOrganization;
	
	/** 支付通道代码 */
	private String payWap;
	
	/** 支付方式代码 */
	private String payType;
	
	/** 交易金额 */
	private Integer totalFee;
	
	/** 交易完成时间 */
	private String payTime;
	
	/** 用于退出消费者循环 */
	private int count;

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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "PayTransReconReq [servPltfmCode=" + servPltfmCode
				+ ", orderId=" + orderId + ", transType=" + transType
				+ ", requestId=" + requestId + ", submitTime=" + submitTime
				+ ", payOrganization=" + payOrganization + ", payWap=" + payWap
				+ ", payType=" + payType + ", totalFee=" + totalFee
				+ ", payTime=" + payTime + "]";
	}
	
	public PayTransReconReq newEntity(){
		return new PayTransReconReq();
	}
}
