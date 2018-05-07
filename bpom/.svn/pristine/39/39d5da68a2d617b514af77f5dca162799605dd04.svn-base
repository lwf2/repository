package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.aspire.bpom.bean.TimeNotifyPO;
import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 签约解约结果通知  支付网关--》订单模块
 * @author chenpeng
 *
 */
@XmlRootElement(name = "msgReq")
@XStreamAlias("msgReq")
public class EntrustPayNotifyReq extends TimeNotifyPO implements Serializable{

	private static final long serialVersionUID = -7600348435005289641L;

	private String msgType;
	
	private String msgVer;
	
	private String requestId;
	
	private String wxPlan_id;
	
	private String wxContract_code;
	
	private String change_type;
	
	private String wxContract_id;
	
	private String wxContract_expired;
	
	private String wxOperate_time;
	
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

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
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

	public String getChange_type() {
		return change_type;
	}

	public void setChange_type(String change_type) {
		this.change_type = change_type;
	}

	public String getWxContract_id() {
		return wxContract_id;
	}

	public void setWxContract_id(String wxContract_id) {
		this.wxContract_id = wxContract_id;
	}

	public String getWxContract_expired() {
		return wxContract_expired;
	}

	public void setWxContract_expired(String wxContract_expired) {
		this.wxContract_expired = wxContract_expired;
	}

	public String getWxOperate_time() {
		return wxOperate_time;
	}

	public void setWxOperate_time(String wxOperate_time) {
		this.wxOperate_time = wxOperate_time;
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
		return "EntrustPayNotifyReq [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", requestId=" + requestId + ", wxPlan_id=" + wxPlan_id
				+ ", wxContract_code=" + wxContract_code + ", change_type="
				+ change_type + ", wxContract_id=" + wxContract_id
				+ ", wxContract_expired=" + wxContract_expired
				+ ", wxOperate_time=" + wxOperate_time + ", wxOpenid="
				+ wxOpenid + "]";
	}
	
}
