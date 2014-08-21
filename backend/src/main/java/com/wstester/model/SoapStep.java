package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoapStep extends Step{

	private static final long serialVersionUID = 1L;

	public SoapStep() {
		uuid = UUID.randomUUID().toString();
	}
}
