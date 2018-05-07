package com.aspire.bpom.xml.bean.request;

/**
 * 签约对账
 * 支付网关-计费平台
 * @author liuweifeng
 *
 */
public class SignRelationshipReq {

	/** 业务平台ID */
	private String servPltfmCode;
	/** 委托代扣协议id */
	private String wxContract_id;
	/** 协议模板id */
	private String wxPlan_id;
	/** 签约协议号 */
	private String wxContract_code;
	/** 协议状态 */
	private String contract_state;
	/** 协议签署时间 */
	private String contract_signed_time;
	/** 协议到期时间 */
	private String contract_expired_time;
	/** 协议解约时间 */
	private String contract_terminated_time;
	/** 协议解约方式 */
	private String contract_termination_mode;
	/** 解约备注 */
	private String CancelRemark;
	/** 微信用户标识 */
	private String wxOpenid;	
	/** 用于退出消费者循环 */
	private int count;
	
	public String getServPltfmCode() {
		return servPltfmCode;
	}
	public void setServPltfmCode(String servPltfmCode) {
		this.servPltfmCode = servPltfmCode;
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
	public String getContract_state() {
		return contract_state;
	}
	public void setContract_state(String contract_state) {
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "SignRelationshipReq [servPltfmCode=" + servPltfmCode
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
	
	public SignRelationshipReq newEntity(){
		return new SignRelationshipReq();
	}
}
