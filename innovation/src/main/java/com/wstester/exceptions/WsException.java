package com.wstester.exceptions;

public class WsException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WsErrorCode werrorCode;

	public WsException(WsErrorCode errorCode) {
		super();
		this.werrorCode = errorCode;
	}

	public WsException(String message, Throwable cause, WsErrorCode errorCode) {
		super(message, cause);
		this.werrorCode = errorCode;
	}

	public WsException(String message, WsErrorCode errorCode) {
		super(message);
		this.werrorCode = errorCode;
	}

	public WsException(Throwable cause, WsErrorCode errorCode) {
		super(cause);
		this.werrorCode = errorCode;
	}

	public WsErrorCode getErrorCode() {
		return werrorCode;
	}

	public void setErrorCode(WsErrorCode errorCode) {
		this.werrorCode = errorCode;
	}

}
