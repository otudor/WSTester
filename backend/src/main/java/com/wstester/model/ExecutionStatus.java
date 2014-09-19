package com.wstester.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public enum ExecutionStatus implements Serializable {

	PASSED, FAILED
}

