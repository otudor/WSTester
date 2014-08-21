package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestStep extends Step {

	private static final long serialVersionUID = 1L;

	public RestStep() {
		uuid = UUID.randomUUID().toString();
	}
}
