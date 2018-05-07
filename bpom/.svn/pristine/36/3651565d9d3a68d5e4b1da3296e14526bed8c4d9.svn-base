package com.aspire.bpom.xml.bean.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 关闭订单接口 内部业务平台--》订单模块
 * @author chenpeng
 *
 */
@XmlRootElement(name = "ClosePayReq")
@XStreamAlias("ClosePayReq")
public class ClosePayReq implements Serializable{

	private static final long serialVersionUID = 1L;

	private String MsgType;
	
	private String Version;
	
	private String systemCode;
	
	private String orderId;
	
	private String tradeId;
	
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

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getHmac() {
		return hmac;
	}

	public void setHmac(String hmac) {
		this.hmac = hmac;
	}

	public String getTradeId() {
		return tradeId;
	}

	public void setTradeId(String tradeId) {
		this.tradeId = tradeId;
	}

	@Override
	public String toString() {
		return "ClosePayReq [MsgType=" + MsgType + ", Version=" + Version
				+ ", systemCode=" + systemCode + ", orderId=" + orderId
				+ ", tradeId=" + tradeId + ", hmac=" + hmac + "]";
	}

}
