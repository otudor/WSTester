package com.wstester.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Assert implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Object expected;
	private String uuid;
	
	public Assert(){
		uuid = UUID.randomUUID().toString();
	}
	
	public Object getExpected() {
		return expected;
	}

	public void setExpected(Object expected) {
		this.expected = expected;
	}

	public String getID(){
		return this.uuid;
	}

	@Override
	public String toString() {
		return "Assert [expected=" + expected + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((expected == null) ? 0 : expected.hashCode());
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
		Assert other = (Assert) obj;
		if (expected == null) {
			if (other.expected != null)
				return false;
		} else if (!expected.equals(other.expected))
			return false;
		return true;
	}
}
