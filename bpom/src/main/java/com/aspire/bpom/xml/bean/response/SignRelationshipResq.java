package com.aspire.bpom.xml.bean.response;

/**
 * 签约对账
 * 计费平台-任我看平台
 * @author liuweifeng
 *
 */
public class SignRelationshipResq {

	/** 业务平台ID */
	private String servPltfmCode;
	/** 签约协议号 */
	private String contractCode;
	/** 协议模板id */
	private String planId;
	/** 协议状态 */
	private String state;
	/** 协议签署时间 */
	private String signedTime;
	/** 协议到期时间 */
	private String expiredTime;
	/** 协议解约时间 */
	private String unSignTime;
	/** 协议解约方式 */
	private String unSignMode;
	/** 解约备注 */
	private String unSignMemo;
	/** 微信用户标识 */
	private String openId;
	
	public String getServPltfmCode() {
		return servPltfmCode;
	}
	public void setServPltfmCode(String servPltfmCode) {
		this.servPltfmCode = servPltfmCode;
	}
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	@Override
	public String toString() {
		return "SignRelationshipResq [servPltfmCode=" + servPltfmCode
				+ ", contractCode=" + contractCode + ", planId=" + planId
				+ ", state=" + state + ", signedTime=" + signedTime
				+ ", expiredTime=" + expiredTime + ", unSignTime=" + unSignTime
				+ ", unSignMode=" + unSignMode + ", unSignMemo=" + unSignMemo
				+ ", openId=" + openId + "]";
	}
}
