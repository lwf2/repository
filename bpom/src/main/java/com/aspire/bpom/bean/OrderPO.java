package com.aspire.bpom.bean;

import java.io.Serializable;

/**
 * 订单/历史订单实体bean
 * @author chenpeng
 *
 */
public class OrderPO implements Serializable{

	private static final long serialVersionUID = -3118292271195137173L;

	private String orderId;			//订单id
	
	private String orderTime;		//订单时间
	
	private String requestId;		//支付网关记录的交易流水号
	
	private String systemCode;		//内部系统代码
	
	private String servPltfmCode;	//内部业务平台代码
	
	private String payWay;			//支付通道
	
	private String payOrganization;	//支付平台代码
	
	private Integer payType;		//支付方式
	
	private String ipAddress;		//用户IP
	
	private String callbackUrl;		//返回跳转URL
	
	private String outOrderId;		//外部订单id
	
	private String tradeId;			//外部交易id
	
	private String userId;			//用户ID
	
	private String merchantName;	//商户展示名称
	
	private String productId;		//产品代码
	
	private String productName;		//产品名
	
	private String productDesc;		//商品的描述
	
	private String channelId;		//渠道代码
	
	private int amount;				//订单金额,单位：分
	
	private String reserved1;		//附加数据
	
	private String productURL;		//商品展示URL
	
	private String contractCode;	//签约协议号
	
	private String wxOpenid;		//用户标识
	
	private String payTime;			//用户完成支付时间
	
	private Integer payStatus;		//支付状态
	
	private String operateTime;	//操作时间
	
	private String refundDesc;		//退款原因描述
	
	private Integer  state ;				//订单状态

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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	public String getServPltfmCode() {
		return servPltfmCode;
	}

	public void setServPltfmCode(String servPltfmCode) {
		this.servPltfmCode = servPltfmCode;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getPayOrganization() {
		return payOrganization;
	}

	public void setPayOrganization(String payOrganization) {
		this.payOrganization = payOrganization;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCallbackUrl() {
		return callbackUrl;
	}

	public void setCallbackUrl(String callbackUrl) {
		this.callbackUrl = callbackUrl;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getReserved1() {
		return reserved1;
	}

	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}

	public String getProductURL() {
		return productURL;
	}

	public void setProductURL(String productURL) {
		this.productURL = productURL;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}

	public String getPayTime() {
		return payTime;
	}

	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getRefundDesc() {
		return refundDesc;
	}

	public void setRefundDesc(String refundDesc) {
		this.refundDesc = refundDesc;
	}

	public String getOutOrderId() {
		return outOrderId;
	}

	public void setOutOrderId(String outOrderId) {
		this.outOrderId = outOrderId;
	}
	
}
