package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MySQLStep extends Step {

	private static final long serialVersionUID = 1L;
    private String operation;

	public String getOperation() {
		return operation;
	}


	public void setOperation(String operation) {
		this.operation = operation;
	}


	public MySQLStep() {
		uuid = UUID.randomUUID().toString();
	}
}
