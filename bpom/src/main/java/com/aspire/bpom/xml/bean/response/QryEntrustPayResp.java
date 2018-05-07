package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信委托代扣签约状态查询
 * 支付网关-MM门户/计费网关
 * @author liuweifeng
 *
 */
@XmlRootElement(name = "msgResp")
@XStreamAlias("msgResp")
public class QryEntrustPayResp implements Serializable {

	private static final long serialVersionUID = 302053169207028891L;
	
	/** 消息类型  填写“QryEntrustPayResp” */
	private String msgType;
	/** 该接口消息的版本号，本次所有的接口消息的版本都为“1.0”  */
	private String msgVer;
	/** 返回码   0表示处理成功，1表示处理失败  */
	private Integer returnCode;
	/** returnCode取值为1时有效,返回错误信息  */
	private String returnMsg;
	/** 委托代扣协议id,签约成功后，微信返回的委托代扣协议id */
	private String wxContract_id;
	/** 协议模板id  */
	private String wxPlan_id;
	/** 签约协议号,商户侧的签约协议号，由商户生成委托代付签约时必填  */
	private String wxContract_code;
	/** 协议状态 0-签约中,1-解约  */
	private Integer contract_state;
	/** 协议签署时间  */
	private String contract_signed_time;
	/** 协议到期时间  */
	private String contract_expired_time;
	/** 协议解约时间  */
	private String contract_terminated_time;
	/** 
	 * 协议解约方式
	 * 当contract_state=1时，该值有效 
	 * 0-未解约 1-有效期过自动解约 
	 * 2-用户主动解约 
	 * 3-商户API解约 
	 * 4-商户平台解约 
	 * 5-注销 
	 */
	private String contract_termination_mode;
	/** 解约备注,解约原因的备注说明  */
	private String CancelRemark;
	/** 微信用户标识  */
	private String wxOpenid;
	
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
	public Integer getContract_state() {
		return contract_state;
	}
	public void setContract_state(Integer contract_state) {
		this.contract_state = contract_state;
	}
	public String getContract_signed_time() {
		return contract_signed_time;
	}
	public void setContract_signed_time(String contract_signed_time) {
		this.contract_signed_time = contract_signed_time;
	}
	public String getContract_expired_time() {
		return contract_expired_time;
	}
	public void setContract_expired_time(String contract_expired_time) {
		this.contract_expired_time = contract_expired_time;
	}
	public String getContract_terminated_time() {
		return contract_terminated_time;
	}
	public void setContract_terminated_time(String contract_terminated_time) {
		this.contract_terminated_time = contract_terminated_time;
	}
	public String getContract_termination_mode() {
		return contract_termination_mode;
	}
	public void setContract_termination_mode(String contract_termination_mode) {
		this.contract_termination_mode = contract_termination_mode;
	}
	public String getCancelRemark() {
		return CancelRemark;
	}
	public void setCancelRemark(String cancelRemark) {
		CancelRemark = cancelRemark;
	}
	public String getWxOpenid() {
		return wxOpenid;
	}
	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "QryEntrustPayResp [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
				+ ", wxContract_id=" + wxContract_id + ", wxPlan_id="
				+ wxPlan_id + ", wxContract_code=" + wxContract_code
				+ ", contract_state=" + contract_state
				+ ", contract_signed_time=" + contract_signed_time
				+ ", contract_expired_time=" + contract_expired_time
				+ ", contract_terminated_time=" + contract_terminated_time
				+ ", contract_termination_mode=" + contract_termination_mode
				+ ", CancelRemark=" + CancelRemark + ", wxOpenid=" + wxOpenid
				+ "]";
	}

}
