package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Assert {
	
	private Object asserts;
	private String uuid;
	
	public Assert(){
		uuid = UUID.randomUUID().toString();
	}
	
	public Object getAsserts() {
		return asserts;
	}

	public void setAsserts(Object asserts) {
		this.asserts = asserts;
	}

	public String getID(){
		return this.uuid;
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
		if (asserts == null) {
			if (other.asserts != null)
				return false;
		} else if (!asserts.equals(other.asserts))
			return false;
		return true;
	}
}
