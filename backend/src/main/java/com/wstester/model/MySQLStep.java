package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MySQLStep extends Step {

	private static final long serialVersionUID = 1L;

	public MySQLStep() {
		uuid = UUID.randomUUID().toString();
	}
}
