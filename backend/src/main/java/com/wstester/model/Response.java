package com.wstester.model;

import java.io.Serializable;

public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	private String stepID;
	private String content;
	private boolean pass;
	
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
	
	public boolean isPass() {
		return pass;
	}
	
	public void setPass(boolean pass) {
		this.pass = pass;
	}

	@Override
	public String toString() {
		return "Response [stepID=" + stepID + ", content=" + content + ", pass=" + pass + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + (pass ? 1231 : 1237);
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
		if (pass != other.pass)
			return false;
		if (stepID == null) {
			if (other.stepID != null)
				return false;
		} else if (!stepID.equals(other.stepID))
			return false;
		return true;
	}
}
