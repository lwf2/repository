package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 支付结果响应  订单模块--》内部业务系统
 * @author chenpeng
 *
 */
@XmlRootElement(name = "PayOrderResultNotifyResp")
@XStreamAlias("PayOrderResultNotifyResp")
public class PayOrderResultNotifyResp implements Serializable{

	private static final long serialVersionUID = -3588845966000382490L;

	private String MsgType;
	
	private String Version;
	
	private Integer returnCode;
	
	private String returnMsg;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "PayResultNotifyResps [MsgType=" + MsgType + ", Version="
				+ Version + ", returnCode=" + returnCode + ", returnMsg="
				+ returnMsg + "]";
	}
	
}
