package com.wstester.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "assertResponse")
public class AssertResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private long id;
	
	@Column(name = "assertId")
	private String assertId;
	
	@Column(name = "message", columnDefinition ="Text")
	private String message;
	
	@Column(name = "status")
	private AssertStatus status;
	
	@ManyToOne
    @JoinColumn(name = "responseId")
    private Response response;
	
	public AssertResponse() {
		super();
	}
	
	public AssertResponse(String assertId, String message, AssertStatus status) {
		this.assertId = assertId;
		this.message = message;
		this.status = status;
	}
	
	public String getAssertId() {
		return assertId;
	}
	
	public void setAssertId(String assertId) {
		this.assertId = assertId;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public AssertStatus getStatus() {
		return status;
	}
	
	public void setStatus(AssertStatus status) {
		this.status = status;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	@Override
	public String toString() {
		return "AssertResponse [assertId=" + assertId + ", message=" + message + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assertId == null) ? 0 : assertId.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
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
		AssertResponse other = (AssertResponse) obj;
		if (assertId == null) {
			if (other.assertId != null)
				return false;
		} else if (!assertId.equals(other.assertId))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (status != other.status)
			return false;
		return true;
	}
}
