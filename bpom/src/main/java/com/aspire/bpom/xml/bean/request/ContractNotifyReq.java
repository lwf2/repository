package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 通知签约接口请求    订单模块-->内部业务平台
 * @author chenpeng
 *
 */
@XmlRootElement(name = "ContractNotifyReq")
@XStreamAlias("ContractNotifyReq")
public class ContractNotifyReq implements Serializable{

	private static final long serialVersionUID = 2019406424048145224L;

	private String MsgType;
	
	private String Version;
	
	private String wxPlan_id;
	
	private String contractCode;
	
	private String wxContract_id;
	
	private String change_type;
	
	private String expiredTime;
	
	private String operateTime;
	
	private String wxOpenid;
	
	private String orderId;
	
	private String tradeId;

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

	public String getWxPlan_id() {
		return wxPlan_id;
	}

	public void setWxPlan_id(String wxPlan_id) {
		this.wxPlan_id = wxPlan_id;
	}

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

	public String getChange_type() {
		return change_type;
	}

	public void setChange_type(String change_type) {
		this.change_type = change_type;
	}

	public String getExpiredTime() {
		return expiredTime;
	}

	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getWxOpenid() {
		return wxOpenid;
	}

	public void setWxOpenid(String wxOpenid) {
		this.wxOpenid = wxOpenid;
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

	public String getWxContract_id() {
		return wxContract_id;
	}

	public void setWxContract_id(String wxContract_id) {
		this.wxContract_id = wxContract_id;
	}

}
