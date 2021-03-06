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
	private String environmentId;
	private List<TestCase> testCaseList;
	private String uuid;

	public TestSuite() {
		uuid = UUID.randomUUID().toString();
		testCaseList = new ArrayList<TestCase>();
	}

	public String getId() {
		return this.uuid;
	}

	public void setId(String id) {
		this.uuid = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(String environmentId) {
		this.environmentId = environmentId;
	}

	public List<TestCase> getTestCaseList() {
		return testCaseList;
	}

	public void setTestCaseList(List<TestCase> testCaseList) {
		this.testCaseList = testCaseList;
	}

	public void addTestCase(TestCase testCase){
		if(this.testCaseList == null){
			this.testCaseList = new ArrayList<TestCase>();
		}
		
		this.testCaseList.add(testCase);
	}

	@Override
	public String toString() {
		return this.name;
	}
	
	public String detailedToString() {
		return "TestSuite [name=" + name + ", environmentId=" + environmentId + ", testCaseList=" + toString(testCaseList) + ", uuid=" + uuid + "]";
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
		result = prime * result + ((environmentId == null) ? 0 : environmentId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((testCaseList == null) ? 0 : testCaseList.hashCode());
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
		if (environmentId == null) {
			if (other.environmentId != null)
				return false;
		} else if (!environmentId.equals(other.environmentId))
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
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
