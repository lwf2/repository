package com.aspire.bpom.xml.common;

import com.aspire.bpom.constants.BpomReturnCode;

public class CCStackInfoResp extends BpomErrorResp{
	
	public CCStackInfoResp(BpomReturnCode code) {
		super(code);
	}

	private String MsgType;
	private String Version;
	private String ReturnCode;
	
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
	public String getReturnCode() {
		return ReturnCode;
	}
	public void setReturnCode(String returnCode) {
		ReturnCode = returnCode;
	}
	
	@Override
	public String toString() {
		return " [MsgType=" + MsgType + ", Version=" + Version + ", ReturnCode="
				+ ReturnCode + "]";
	}
}
