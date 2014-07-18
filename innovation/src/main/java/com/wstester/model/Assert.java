package com.wstester.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Assert {
	
	private Object asserts;

	public Object getAsserts() {
		return asserts;
	}

	public void setAsserts(Object asserts) {
		this.asserts = asserts;
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
