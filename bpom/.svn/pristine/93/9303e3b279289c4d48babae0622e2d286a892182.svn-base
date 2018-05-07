package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 申请解约接口
 * 内部业务平台-订单模块
 * @author liuweifeng
 *
 */
@XmlRootElement(name = "CancelContractReq")
@XStreamAlias("CancelContractReq")
public class CancelContractReq implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 消息类型  */
	private String MsgType;
	/** 该接口消息的版本号，本次所有的接口消息的版本都为“1.0.0”  */
	private String Version;
	/** 内部系统代码  */
	private String systemCode;
	/** 协议模板id  */
	private String wxPlan_id;
	/** 签约协议号  */
	private String contractCode;
	/** 解约备注，解约原因的备注说明 */
	private String unSignMemo;
	/** 数字签名 对以上所有字段按照定义的顺序取值再与网元密钥拼接相连，
	 * 然后进行MD5摘要算法所得值，最后转换成16进制串（大写）  */
	private String hmac;
	
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
	public String getSystemCode() {
		return systemCode;
	}
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
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
	public String getUnSignMemo() {
		return unSignMemo;
	}
	public void setUnSignMemo(String unSignMemo) {
		this.unSignMemo = unSignMemo;
	}
	public String getHmac() {
		return hmac;
	}
	public void setHmac(String hmac) {
		this.hmac = hmac;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
//	
	@Override
	public String toString() {
		return "CancelContractReq [MsgType=" + MsgType + ", Version=" + Version
				+ ", systemCode=" + systemCode + ", wxPlan_id=" + wxPlan_id
				+ ", contractCode=" + contractCode + ", unSignMemo="
				+ unSignMemo + ", hmac=" + hmac + "]";
	}
}
