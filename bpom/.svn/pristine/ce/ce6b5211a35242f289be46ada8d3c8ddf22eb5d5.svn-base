package com.aspire.bpom.xml.common;

import com.aspire.bpom.constants.BpomReturnCode;

public class ErrorResp extends CommonResp{
	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public ErrorResp(BpomReturnCode code){
		super.code = code.getCode();
		super.des = code.getMessage();
	}

	@Override
	public String toString() {
		return "result=" + result + ", code=" + code + ", des="
				+ des;
	}
}
