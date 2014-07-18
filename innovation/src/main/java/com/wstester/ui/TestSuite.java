package com.wstester.ui;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestSuite {
	
	private Environment environment;
	private List<TestCase> testCaseList;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Environment getEnvironment() {
		return environment;
	}
	
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}
	
	public List<TestCase> getTestCaseList() {
		return testCaseList;
	}
	
	public void setTestCaseList(List<TestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}
}
