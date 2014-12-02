package com.wstester.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ServiceStatus {

	AVAILABLE, UNAVAILABLE, MOCKED;
}
