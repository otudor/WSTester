package com.wstester.ui;

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
}
