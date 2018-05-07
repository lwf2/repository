package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 签约结果通知接口响应  内部业务系统--》订单模块
 * @author chenpeng
 *
 */
@XmlRootElement(name = "ContractNotifyResp")
@XStreamAlias("ContractNotifyResp")
public class ContractNotifyResp implements Serializable{

	private static final long serialVersionUID = -6507963709423722936L;

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
		return "ContractNotifyResp [MsgType=" + MsgType + ", Version="
				+ Version + ", returnCode=" + returnCode + ", returnMsg="
				+ returnMsg + "]";
	}
	
	
}
