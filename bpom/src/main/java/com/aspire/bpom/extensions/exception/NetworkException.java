package com.aspire.bpom.extensions.exception;


public class NetworkException extends BusinessException {
	private static final long serialVersionUID = -3016962938456850451L;

	public NetworkException(String errorCode, Object ... args) {
		super(errorCode, args);
	}

	public NetworkException(String message, Throwable cause) {
		super(message, cause);
	}
}
