package com.wstester.importer.rest;

public class RestResponseMessage {

    protected int code;
    protected String message;
    protected String responseModel;
    protected int hashCode;

    public int getCode() {
        return code;
    }

    public void setCode(int value) {
        this.code = value;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String value) {
        this.message = value;
    }

    public String getResponseModel() {
        return responseModel;
    }

    public void setResponseModel(String value) {
        this.responseModel = value;
    }

    public int getHashCode() {
        return hashCode;
    }

    public void setHashCode(int value) {
        this.hashCode = value;
    }

}
