package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 微信委托代扣签约状态查询
 * MM门户/计费网关-支付网关
 * 查询签约关系接口提供单笔签约关系查询，支付网关提供两种模式的查询：
 * contract_id模式：传入委托代扣协议id进行查询
 * plan_id+contract_code模式：传入模板id和委托代扣协议号进行查询
 * @author liuweifeng
 *
 */
@XmlRootElement(name = "msgReq")
@XStreamAlias("msgReq")
public class QryEntrustPayReq implements Serializable {

	private static final long serialVersionUID = -8892557039655320035L;

	/** 消息类型 填写“QryEntrustPayReq”  */
	private String msgType;
	/** 该接口消息的版本号，本次所有的接口消息的版本都为“1.0”  */
	private String msgVer;
	/** 接入支付网关内部网元代码  */
	private String systemCode;
	/** 协议模板id  */
	private String wxPlan_id;
	/** 签约协议号     商户侧的签约协议号，由商户生成委托代付签约时必填  */
	private String wxContract_code;
	
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	@Override
	public String toString() {
		return "QryEntrustPayReq [msgType=" + msgType + ", msgVer=" + msgVer
				+ ", systemCode=" + systemCode + ", wxPlan_id=" + wxPlan_id
				+ ", wxContract_code=" + wxContract_code + "]";
	}
}
