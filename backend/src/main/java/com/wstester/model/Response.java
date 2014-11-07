package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	private String stepID;
	private String content;
	private ExecutionStatus status;
	private String errorMessage;
	private List<AssertResponse> assertResponseList;
	private Map<String, String> headerMap;
	
	public String getStepID() {
		return stepID;
	}
	
	public void setStepID(String stepID) {
		this.stepID = stepID;
		assertResponseList = new ArrayList<AssertResponse>();
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public ExecutionStatus getStatus() {
		return status;
	}

	public void setStatus(ExecutionStatus status) {
		this.status = status;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<AssertResponse> getAssertResponseList() {
		return assertResponseList;
	}

	public void setAssertResponseList(List<AssertResponse> assertResponseList) {
		this.assertResponseList = assertResponseList;
	}

	public void addAssertResponse(AssertResponse assertResponse){
		this.assertResponseList.add(assertResponse);
	}
	
	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	@Override
	public String toString() {
		return "Response [stepID=" + stepID + ", content=" + content + ", status=" + status + ", errorMessage=" + errorMessage + ", assertResponseList=" + assertResponseList + ", headerMap="
				+ headerMap + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assertResponseList == null) ? 0 : assertResponseList.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((headerMap == null) ? 0 : headerMap.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Response other = (Response) obj;
		if (assertResponseList == null) {
			if (other.assertResponseList != null)
				return false;
		} else if (!assertResponseList.equals(other.assertResponseList))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (headerMap == null) {
			if (other.headerMap != null)
				return false;
		} else if (!headerMap.equals(other.headerMap))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
}
