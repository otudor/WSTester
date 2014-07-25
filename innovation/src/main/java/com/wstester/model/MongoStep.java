package com.wstester.model;

import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MongoStep extends Step{

	public MongoStep() {
		uuid = UUID.randomUUID().toString();
	}
}
