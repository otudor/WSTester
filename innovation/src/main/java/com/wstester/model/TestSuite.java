package com.wstester.model;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestSuite {

	private Environment environment;
	private List<TestCase> testCaseList;
	private String name;

	private String uuid;

	public TestSuite() {
		uuid = UUID.randomUUID().toString();
	}

	public String getID() {
		return this.uuid;
	}

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestSuite other = (TestSuite) obj;
		if (environment == null) {
			if (other.environment != null)
				return false;
		} else if (!environment.equals(other.environment))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (testCaseList == null) {
			if (other.testCaseList != null)
				return false;
		} else if (!testCaseList.equals(other.testCaseList))
			return false;
		return true;
	}

}
