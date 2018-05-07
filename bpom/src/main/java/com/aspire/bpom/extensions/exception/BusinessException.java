/*
 *  Copyright (c)  2004-2009 Aspire Info, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of Aspire
 *  Info, Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into
 *  with Aspire.
 */
package com.aspire.bpom.extensions.exception;

/**
 * 业务逻辑异常 。
 * <p>
 * 业务层的每个方法都可能抛出该异常，该异常一般是在Action层的入口方法中捕获处理，
 * 业务层没有必须的理由不要处理该异常。
 * </p>
 * 
 */
public class BusinessException extends Exception {
	private static final long serialVersionUID = 2678145046723789238L;
	private String errorCode = null;
	private Object[] args = null;
	private String message = null;
	
	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @return the args
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * 返回详细的错误消息。
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * 构造函数
	 * 
	 * @param errorCode - 错误消息码
	 * @param args - 错误消息参数
	 */
	public BusinessException(String errorCode, Object ... args) {
		super();
		this.errorCode = errorCode;
		this.args = args;
		
//		message = "【" + errorCode + "】" + MessageFormat.format(MessageHelper.getInstance().getMessage(errorCode), args);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public BusinessException(String message, Throwable cause) {
		super(cause);
		
		this.message = message;
	}

}

