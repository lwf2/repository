package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 查询签约关系     
 * 订单模块-内部业务平台
 * @author liuweifeng
 *
 */
@XmlRootElement(name = "QryContractResp")
@XStreamAlias("QryContractResp")
public class QryContractResp implements Serializable {

	private static final long serialVersionUID = -4086406158980835712L;
	
	/** 消息类型  */
	private String MsgType;
	/** 该接口消息的版本号，本次所有的接口消息的版本都为“1.0.0” */
	private String Version;
	/** 0：成功 ，1：失败  */
	private Integer returnCode;
	/** 返回错误信息  */
	private String returnMsg;
	/** 签约协议号  */
	private String contractCode;
	/** 状态 0：签约成功，1：解约&过期  */
	private Integer state;
	/** 协议模板id  */
	private String wxPlan_id;
	/** 微信用户标识  */
	private String wxOpenid;
	/** 协议签署时间  */
	private String signedTime;
	/** 协议到期时间  */
	private String expiredTime;
	/** 协议解约时间  */
	private String unSignTime;
	/** 协议解约方式 
	 * 当contract_state=1时，该值有效
	 *  0-未解约 
	 *  1-有效期过自动解约 
	 *  2-用户主动解约 
	 *  3-商户API解约 
	 *  4-商户平台解约 
	 *  5-注销  
	 */
	private String unSignMode;
	/** 解约备注  解约原因的备注说明  */
	private String unSignMemo;
	
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
	public String getContractCode() {
		return contractCode;
	}
	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getWxPlan_id() {
		return wxPlan_id;
	}
	public void setWxPlan_id(String wxPlan_id) {
		this.wxPlan_id = wxPlan_id;
	}
	public String getWxOpenid() {
		return wxOpenid;
	}
	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "QryContractResp [MsgType=" + MsgType + ", Version=" + Version
				+ ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
				+ ", contractCode=" + contractCode + ", state=" + state
				+ ", wxPlan_id=" + wxPlan_id + ", wxOpenid=" + wxOpenid
				+ ", signedTime=" + signedTime + ", expiredTime=" + expiredTime
				+ ", unSignTime=" + unSignTime + ", unSignMode=" + unSignMode
				+ ", unSignMemo=" + unSignMemo + "]";
	}
}
