package com.wstester.model;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Asset {
	private String uuid;

	public Asset() {
		uuid = UUID.randomUUID().toString();
	}

	public String getID() {
		return this.uuid;
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}
}
