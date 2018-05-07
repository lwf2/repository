package com.aspire.bpom.bean;

import java.io.Serializable;

/**
 * 签约实体bean
 * @author chenpeng
 *
 */
public class SignPO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String contractCode;		//签约协议号
	
	private String contractId;			//委托代扣协议id
	
	private String signedTime;			//签约时间
	
	private String planId;				//模板id
	
	private String displayName;			//展示名称
	
	private String expiredTime;			//协议到期时间
	
	private String systemCode;			//内部系统代码
	
	private String servPltfmCode;		//内部业务平台代码
	
	private String userId;				//用户id
	
	private String openId;				//微信用户标识
	
	private String productId;			//产品代码
	
	private String productName;			//产品名
	
	private String productDesc;			//对商品的描述
	
	private String channelId;			//渠道代码
	
	private String reserved1;			//交易返回时原样返回给商家
	
	private String productURL;			//商品展示链接地址
	
	private String unSignTime;			//解约时间
	
	private String unSignMode;			//协议解约方式
	
	private String unSignMemo;			//解约备注
	
	private Integer state;					//状态 0：未签约 1：签约成功 2：解约&过期

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getPlanId() {
		return planId;
	}

	public void setPlanId(String planId) {
		this.planId = planId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getSignedTime() {
		return signedTime;
	}

	public void setSignedTime(String signedTime) {
		this.signedTime = signedTime;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
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

	public String getUnSignTime() {
		return unSignTime;
	}

	public void setUnSignTime(String unSignTime) {
		this.unSignTime = unSignTime;
	}

	public String getUnSignMode() {
		return unSignMode;
	}

	public void setUnSignMode(String unSignMode) {
		this.unSignMode = unSignMode;
	}

	public String getUnSignMemo() {
		return unSignMemo;
	}

	public void setUnSignMemo(String unSignMemo) {
		this.unSignMemo = unSignMemo;
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
	
}
