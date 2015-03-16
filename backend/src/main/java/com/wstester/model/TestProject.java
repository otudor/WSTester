package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestProject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;
	private List<TestSuite> testSuiteList;
	private List<Asset> assetList;
	private List<Environment> environmentList;
	private List<Variable> variableList;

	public TestProject() {
		id = UUID.randomUUID().toString();
		environmentList = new ArrayList<Environment>();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public List<TestSuite> getTestSuiteList() {
		if(this.testSuiteList == null) {
			this.testSuiteList = new ArrayList<TestSuite>();
		}
		return testSuiteList;
	}

	public void setTestSuiteList(List<TestSuite> testSuiteList) {
		this.testSuiteList = testSuiteList;
	}
	
	public List<Asset> getAssetList() {
		return assetList;
	}

	public void setAssetList(List<Asset> assetList) {
		this.assetList = assetList;
	}

	public void addAsset(Asset asset){
		if (this.assetList == null) {
				this.assetList = new ArrayList<Asset>();
		}
		
		this.assetList.add(asset);
	}

	public List<Environment> getEnvironmentList() {
		return environmentList;
	}

	public void setEnvironmentList(List<Environment> environmentList) {
		this.environmentList = environmentList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Variable> getVariableList() {
		if(this.variableList == null) {
			this.variableList = new ArrayList<Variable>();
		}
		return variableList;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}	

	@Override
	public String toString() {
		return "TestProject [name=" + name + ", testSuiteList=" + toStringSuite(testSuiteList) + ", assetList=" + assetList+ ", environmentList=" + toStringEnv(environmentList)
				+ ", variableList=" + variableList + ", uuid=" + id + "]";
	}

	private String toStringEnv(List<Environment> environmentList) {

		if (environmentList == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder("[");
		for (Environment environment : environmentList) {
			if (environmentList.indexOf(environment) != 0) {
				sb.append(", ");
			}
			sb.append(environment.detailedToString());
		}
		sb.append("]");

		return sb.toString();
	}

	private String toStringSuite(List<TestSuite> testSuiteList) {

		if (testSuiteList == null) {
			return "null";
		}

		StringBuilder sb = new StringBuilder("[");
		for (TestSuite testSuite : testSuiteList) {
			if (testSuiteList.indexOf(testSuite) != 0) {
				sb.append(", ");
			}
			sb.append(testSuite.detailedToString());
		}
		sb.append("]");

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((assetList == null) ? 0 : assetList.hashCode());
		result = prime * result + ((environmentList == null) ? 0 : environmentList.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((testSuiteList == null) ? 0 : testSuiteList.hashCode());
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
		TestProject other = (TestProject) obj;
		if (assetList == null) {
			if (other.assetList != null)
				return false;
		} else if (!assetList.equals(other.assetList))
			return false;
		if (environmentList == null) {
			if (other.environmentList != null)
				return false;
		} else if (!environmentList.equals(other.environmentList))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (testSuiteList == null) {
			if (other.testSuiteList != null)
				return false;
		} else if (!testSuiteList.equals(other.testSuiteList))
			return false;
		if (variableList == null) {
			if (other.variableList != null)
				return false;
		} else if (!variableList.equals(other.variableList))
			return false;
		return true;
	}
}
