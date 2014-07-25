package com.wstester.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ServiceType {

	REST, SOAP, MYSQL, MONGO
}
