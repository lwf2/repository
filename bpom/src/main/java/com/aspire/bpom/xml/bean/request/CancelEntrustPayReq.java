package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信委托代扣解约申请
 * MM门户/计费网关-支付网关
 * @author liuweifeng
 *
 */
@XmlRootElement(name = "msgReq")
@XStreamAlias("msgReq")
public class CancelEntrustPayReq implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 消息类型 填写“CancelEntrustPayReq”  */
	private String msgType;
	/** 该接口消息的版本号，本次所有的接口消息的版本都为“1.0”  */
	private String msgVer;
	/** 接入支付网关内部网元代码  */
	private String systemCode;
	/** 内部业务平台代码  */
	private String servPltfmCode;
	/** 支付机构  */
	private String payOrganization;
	/** 委托代扣协议id  */
	private String wxContract_id;
	/** 协议模板id  */
	private String wxPlan_id;
	/** 签约协议号     商户侧的签约协议号，由商户生成委托代付签约时必填  */
	private String wxContract_code;
	/** 解约备注,解约原因的备注说明  */
	private String CancelRemark;
	
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
	public String getPayOrganization() {
		return payOrganization;
	}
	public void setPayOrganization(String payOrganization) {
		this.payOrganization = payOrganization;
	}
	public String getWxContract_id() {
		return wxContract_id;
	}
	public void setWxContract_id(String wxContract_id) {
		this.wxContract_id = wxContract_id;
	}
	public String getWxPlan_id() {
		return wxPlan_id;
	}
	public void setWxPlan_id(String wxPlan_id) {
		this.wxPlan_id = wxPlan_id;
	}
	public String getWxContract_code() {
		return wxContract_code;
	}
	public void setWxContract_code(String wxContract_code) {
		this.wxContract_code = wxContract_code;
	}
	public String getCancelRemark() {
		return CancelRemark;
	}
	public void setCancelRemark(String cancelRemark) {
		CancelRemark = cancelRemark;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "CancelEntrustPayReq [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", systemCode=" + systemCode + ", servPltfmCode="
				+ servPltfmCode + ", payOrganization=" + payOrganization
				+ ", wxContract_id=" + wxContract_id + ", wxPlan_id="
				+ wxPlan_id + ", wxContract_code=" + wxContract_code
				+ ", CancelRemark=" + CancelRemark + "]";
	}
}
