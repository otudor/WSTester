package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestSuite implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private Environment environment;
	private List<TestCase> testCaseList;
	private List<Variable> variableList;
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
		if(this.environment == null){
			this.environment = new Environment();
		}
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public List<TestCase> getTestCaseList() {
		if(this.testCaseList == null){
			this.testCaseList = new ArrayList<TestCase>();
		}
		return testCaseList;
	}

	public void setTestCaseList(List<TestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}

	public List<Variable> getVariableList() {
		if(this.variableList == null){
			this.variableList = new ArrayList<Variable>();
		}
		return variableList;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public String detailedToString() {
		return "TestSuite [name=" + name + ", environment=" + environment.detailedToString() + ", testCaseList=" + toString(testCaseList) + ", variableList=" + variableList + ", uuid=" + uuid + "]";
	}

	private String toString(List<TestCase> testCaseList) {
		
		if(testCaseList == null){
			return "null";
		}
		
		StringBuilder sb = new StringBuilder("[");
		for(TestCase testCase : testCaseList){
			if(testCaseList.indexOf(testCase) != 0){
				sb.append(", ");
			}
			sb.append(testCase.detailedToString());
		}
		
		sb.append("]");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((environment == null) ? 0 : environment.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((testCaseList == null) ? 0 : testCaseList.hashCode());
		result = prime * result + ((variableList == null) ? 0 : variableList.hashCode());
		return result;
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
		if (variableList == null) {
			if (other.variableList != null)
				return false;
		} else if (!variableList.equals(other.variableList))
			return false;
		return true;
	}
}
