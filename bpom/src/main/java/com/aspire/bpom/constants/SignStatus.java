package com.aspire.bpom.constants;

/**
 * 签约状态
 * @author chenpeng
 *
 */
public interface SignStatus {

	public static final Integer UN_SIGN = 0;		//未签约
	
	public static final Integer SUCCESS_SIGN = 1;	//签约成功
	
	public static final Integer SURRENDER_SIGN = 2;	//解约&过期
}
