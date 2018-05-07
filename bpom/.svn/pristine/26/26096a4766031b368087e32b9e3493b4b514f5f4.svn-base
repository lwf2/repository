package com.aspire.bpom.constants;

public enum BpomReturnCode implements EnumPower{
	/******************************************************************************/
	BSM_SUCCESS("0", "Success"),
	BSM_FAIL("1", "Fail"),
	UNDEFINED_FAIL("10000","UNDEFINED_FAIL"),
	AUTHENTICATION_FAILED("10","身份认证失败"),
	IP_AUTHENTICATION_FAILED("11", "IP认证失败"),
	ACCESS_RIGHTS_VALIDATION_FAILED("12","访问权限验证失败"),
	REQUEST_PARAMETER_ERROR_OR_MISSING("13","请求参数错误/缺失"),
	ACCESS_INTERFACE_DOES_NOT_EXIS("14","访问接口不存在"),
	INTERNAL_INTERFACE_FLOW_CONTROL("20","内部接口流控超限额禁止访问"),
	EXTERNAL_INTERFACE_FLOW_CONTROL("21","外部接口流控超限额禁止访问"),
	EXTERNAL_INTERFACE_REQUEST_FAILED("30","外部接口请求失败"),
	EXTERNAL_INTERFACE_REQUEST_TIMEOUT ("31","外部接口请求超时"),
	EXTERNAL_SYSTEM_RETURNS_PARAMETER_EXCEPTION("32","外部系统返回参数异常"),
	SYSTEM_INTERNAL_ERROR("99","系统内部错误"),

	HEADER_LESS_PARAMS("1002", "uis header less params"),
	HEADER_IS_NULL("1001", "get header(x-uis-authentication) is null"),
	HEADER_MISS_SYSTEMCODE("1003", " header missing systemcode"),
	HEADER_MISS_INTERFACECODE("1004", "header missing interfacecode"),
	HEADER_MISS_SIGN("1005", "header missing sign"),
	HEADER_MISS_TIMESTAMP("1006","header miss timestamp"),
	
	;
	private String code;
	private String msg;
	
	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return code;
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return msg;
	}
	
	BpomReturnCode(String code, String msg){
		this.code = code;
		this.msg = msg;
	}
}

