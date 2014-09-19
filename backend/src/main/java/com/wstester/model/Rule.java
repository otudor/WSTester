package com.wstester.model;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso(RestRule.class)
public interface Rule {

	String run(Step step);
}
