package com.wstester.model;

import java.io.Serializable;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Assert implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String actual;
	private AssertOperation operation;
	private String expected;
	
	public Assert(){
		uuid = UUID.randomUUID().toString();
	}
	
	public String getExpected() {
		return expected;
	}

	public void setExpected(String expected) {
		this.expected = expected;
	}

	public String getActual() {
		return actual;
	}

	public void setActual(String actual) {
		this.actual = actual;
	}

	public AssertOperation getOperation() {
		return operation;
	}

	public void setOperation(AssertOperation operation) {
		this.operation = operation;
	}

	public String getId(){
		return this.uuid;
	}
	
	public void setId(String id){
		this.uuid = id;
	}

	@Override
	public String toString() {
		return "Assert [uuid=" + uuid + ", actual=" + actual + ", operation=" + operation + ", expected=" + expected + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((actual == null) ? 0 : actual.hashCode());
		result = prime * result	+ ((expected == null) ? 0 : expected.hashCode());
		result = prime * result	+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		if (actual == null) {
			if (other.actual != null)
				return false;
		} else if (!actual.equals(other.actual))
			return false;
		if (expected == null) {
			if (other.expected != null)
				return false;
		} else if (!expected.equals(other.expected))
			return false;
		if (operation != other.operation)
			return false;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}