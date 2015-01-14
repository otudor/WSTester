package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;

@NamedQueries({
	@NamedQuery(name="getAllByStepId", query="FROM Response r WHERE r.stepId = :stepId"),
	@NamedQuery(name="getLastByStepId", query="FROM Response r1 WHERE r1.stepId = :stepId AND r1.runDate = (SELECT max(runDate) FROM Response r2 WHERE r2.stepId = :stepId)"),
})
@Entity
@Table(name = "response")
public class Response implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    private long id;
	
	@Column(name = "stepId")
	private String stepId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "runDate")
	private Date runDate;
	
	@Column(name = "content", columnDefinition ="Text")
	private String content;
	
	@Column(name = "status")
	private ExecutionStatus status;
	
	@Column(name = "errorMessage", columnDefinition ="Text")
	private String errorMessage;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "responseId")
	private List<AssertResponse> assertResponseList;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = false)
	@JoinColumn(name = "responseId")
	private List<Header> headerList;
	
	public String getStepId() {
		return stepId;
	}

	public void setStepId(String stepId) {
		this.stepId = stepId;
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
		if(this.assertResponseList == null) {
			this.assertResponseList = new ArrayList<AssertResponse>();
		}
		
		this.assertResponseList.add(assertResponse);
	}
	
	public List<Header> getHeaderList() {
		return headerList;
	}

	public void setHeaderList(List<Header> headerList) {
		this.headerList = headerList;
	}

	public Date getRunDate() {
		return runDate;
	}

	public void setRunDate(Date runDate) {
		this.runDate = runDate;
	}

	@Override
	public String toString() {
		return "Response [stepId=" + stepId + ", runDate=" + runDate + ", content=" + content + ", status=" + status + ", errorMessage=" + errorMessage + ", assertResponseList=" + assertResponseList
				+ ", headerList=" + headerList + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assertResponseList == null) ? 0 : assertResponseList.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((headerList == null) ? 0 : headerList.hashCode());
		result = prime * result + ((runDate == null) ? 0 : runDate.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((stepId == null) ? 0 : stepId.hashCode());
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
		if (headerList == null) {
			if (other.headerList != null)
				return false;
		} else if (!headerList.equals(other.headerList))
			return false;
		if (runDate == null) {
			if (other.runDate != null)
				return false;
		} else if (!runDate.equals(other.runDate))
			return false;
		if (status != other.status)
			return false;
		if (stepId == null) {
			if (other.stepId != null)
				return false;
		} else if (!stepId.equals(other.stepId))
			return false;
		return true;
	}
}
