package com.aspire.bpom.xml.bean.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 申请解约接口
 * 订单模块-内部业务平台
 * @author liuweifeng
 *
 */
@XmlRootElement(name = "CancelContractResp")
@XStreamAlias("CancelContractResp")
public class CancelContractResp implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 消息类型  */
	private String MsgType;
	/** 该接口消息的版本号，本次所有的接口消息的版本都为“1.0.0” */
	private String Version;
	/** 0：成功 ，1：失败  */
	private Integer returnCode;
	/** 返回错误信息  */
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
		return "CancelContractResp [MsgType=" + MsgType + ", Version="
				+ Version + ", returnCode=" + returnCode + ", returnMsg="
				+ returnMsg + "]";
	}
}
