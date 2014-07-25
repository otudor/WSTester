package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RestStep extends Step {

	public RestStep() {
		uuid = UUID.randomUUID().toString();
	}
}
