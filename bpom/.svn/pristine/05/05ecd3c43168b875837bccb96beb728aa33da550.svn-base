package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 关闭订单接口响应  订单模块--》内部业务系统
 * @author chenpeng
 *
 */
@XmlRootElement(name = "ClosePayResp")
@XStreamAlias("ClosePayResp")
public class ClosePayResp implements Serializable{

	private static final long serialVersionUID = 1L;

	private String MsgType;
	
	private String Version;
	
	private Integer returnCode;
	
	private String returnMsg;
	
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

	@Override
	public String toString() {
		return "ClosePayResp [MsgType=" + MsgType + ", Version=" + Version
				+ ", returnCode=" + returnCode + ", returnMsg=" + returnMsg
				+ ", orderId=" + orderId + ", tradeId=" + tradeId + "]";
	}

}
