package com.wstester.asserts;

import com.wstester.model.Assert;
import com.wstester.model.AssertResponse;

public class AssertModel {

	public Assert asert;
	public AssertResponse assertResponse;
	
	public Assert getAsert() {
		return asert;
	}
	
	public void setAsert(Assert asert) {
		this.asert = asert;
	}
	
	public AssertResponse getAssertResponse() {
		return assertResponse;
	}
	
	public void setAssertResponse(AssertResponse assertResponse) {
		this.assertResponse = assertResponse;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((asert == null) ? 0 : asert.hashCode());
		result = prime * result
				+ ((assertResponse == null) ? 0 : assertResponse.hashCode());
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
		AssertModel other = (AssertModel) obj;
		if (asert == null) {
			if (other.asert != null)
				return false;
		} else if (!asert.equals(other.asert))
			return false;
		if (assertResponse == null) {
			if (other.assertResponse != null)
				return false;
		} else if (!assertResponse.equals(other.assertResponse))
			return false;
		return true;
	}
}
