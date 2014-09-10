package com.wstester.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Execution implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String uuid;
	private Date time;
	private StepStatusType status;
	private Response response;
	
	public Execution(){
		uuid = UUID.randomUUID().toString();
	}
	
	public StepStatusType getStatus() {
		return this.status;
	}

	public void setStatus(StepStatusType status) {
		this.status = status;
	}
	
	public Response getResponse() {
		return this.response;
	}

	public void setResponse(Response r) {
		this.response = r;
	}
	
	public Date getRunDate() {
		return this.time;
	}

	public void setRunDate(Date t) {
		this.time = t;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Execution other = (Execution) obj;
		if (this.time == other.time &&
				this.response == other.response &&
				this.status == other.status)
			return true;
		else 
			return false;

	}
}
