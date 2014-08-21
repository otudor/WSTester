package com.wstester.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ServiceType implements Serializable {

	REST, SOAP, MYSQL, MONGO
}
