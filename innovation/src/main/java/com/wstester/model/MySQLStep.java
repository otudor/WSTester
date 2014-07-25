package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MySQLStep extends Step {

	public MySQLStep() {
		uuid = UUID.randomUUID().toString();
	}
}
