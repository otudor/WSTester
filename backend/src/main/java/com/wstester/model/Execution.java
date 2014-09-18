package com.wstester.model;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Execution implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String uuid;
	private Date runDate;
	private Response response;
	
	public Execution() {
		uuid = UUID.randomUUID().toString();
	}
	
	public String getUuid() {
		return uuid;
	}
	
	public Response getResponse() {
		return this.response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	public Date getRunDate() {
		return this.runDate;
	}

	public void setRunDate(Date date) {
		this.runDate = date;
	}

	@Override
	public String toString() {
		return "Execution [uuid=" + uuid + ", runDate=" + runDate + ", response=" + response + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((runDate == null) ? 0 : runDate.hashCode());
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
		Execution other = (Execution) obj;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (runDate == null) {
			if (other.runDate != null)
				return false;
		} else if (!runDate.equals(other.runDate))
			return false;
		return true;
	}
}
