package com.wstester.model;

import java.io.Serializable;

public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	private String stepID;
	private String content;
	private ExecutionStatus status;
	private String errorMessage;
	
	public String getStepID() {
		return stepID;
	}
	
	public void setStepID(String stepID) {
		this.stepID = stepID;
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

	@Override
	public String toString() {
		return "Response [stepID=" + stepID + ", content=" + content + ", status=" + status + ", errorMessage=" + errorMessage + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stepID == null) ? 0 : stepID.hashCode());
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
		if (status != other.status)
			return false;
		if (stepID == null) {
			if (other.stepID != null)
				return false;
		} else if (!stepID.equals(other.stepID))
			return false;
		return true;
	}
}
