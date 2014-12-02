package com.wstester.exceptions;



public class EContainer {
	/** 
	 * This is a container to carry the error to the UI
	 */
	
	private WsErrorCode errorCode;
    private String errorMessage;
    private String moreInfo;
	public WsErrorCode getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(WsErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getMoreInfo() {
		return moreInfo;
	}
	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

    
    
    
}
