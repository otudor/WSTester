package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SoapStep extends Step{

	public SoapStep() {
		uuid = UUID.randomUUID().toString();
	}
}
