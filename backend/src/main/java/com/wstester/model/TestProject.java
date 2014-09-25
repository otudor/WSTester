package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestProject implements Serializable {

	private static final long serialVersionUID = 1L;
	private String uuid;
	private String name;
	private List<TestSuite> testSuiteList;
	private List<Asset> assetList;
	private List<Environment> environmentList;
	private List<Variable> variableList;

	public TestProject() {
		uuid = UUID.randomUUID().toString();
		testSuiteList = new ArrayList<TestSuite>();
	}

	public String getID() {
		return this.uuid;
	}

	public List<TestSuite> getTestSuiteList() {
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

	public void addAsset(Asset asset) {
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
		return variableList;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}

	public TestSuite getFirstTestSuite() {
		TestSuite result = null;
		if (!testSuiteList.isEmpty()) {
			result = testSuiteList.get(0);
		}
		return result;
	}

	public TestSuite getTestSuite(String uID) {
		TestSuite result = null;
		for (TestSuite entry : testSuiteList) {
			if (entry.getID().equals(uID)) {
				result = entry;
				break;
			}
		}
		return result;
	}

	public void addTestSuite(TestSuite ts) {
		if (testSuiteList == null) {
			testSuiteList = new ArrayList<TestSuite>();
		}
		testSuiteList.add(ts);
	}
	
	public void addStepForTestCase(Step step, String id) {
		for (TestSuite testSuite : testSuiteList) {
			if (testSuite.getTestCaseList() != null && !testSuite.getTestCaseList().isEmpty()) {
				for (TestCase tc : testSuite.getTestCaseList()) {
					if (tc.getID().equals(id)) {
						tc.addStep(step);
					}
				}
			}
		}
		
	}

	public TestCase addTestCaseForTestSuite(String tsUID) {
		TestCase result = null;
		for (TestSuite entry : testSuiteList) {
			if (entry.getID().equals(tsUID)) {
				result = new TestCase();
				result.setName("Test Case");
				entry.getTestCaseList().add(result);
				return result;
			}
		}
		return result;
	}

	public void removeTestCase(String tcUID) {
		for (TestSuite entry : testSuiteList) {
			if (entry != null) {
				for (TestCase tc : entry.getTestCaseList()) {
					if (tc.getID().equals(tcUID)) {
						entry.getTestCaseList().remove(tc);
						break;
					}
				}
			}
		}
	}

	public Step getStep(String stepUID) {
		if (testSuiteList != null && !testSuiteList.isEmpty()){
			for (TestSuite entry : testSuiteList) {
				if (entry.getTestCaseList() != null
						&& !entry.getTestCaseList().isEmpty()){
					for (TestCase tc : entry.getTestCaseList()) {
						if (tc.getStepList() != null
								&& !tc.getStepList().isEmpty()){
							for (Step stp : tc.getStepList())
								if (stp.getID().equals(stepUID)){
									return stp;
								}
						}
					}	
				}
			}
		}
		return null;
	}
	
	public void setStepByUID(Step src, String stepUID) {
		for (TestSuite entry : testSuiteList) {
			if (entry.getTestCaseList() != null && !entry.getTestCaseList().isEmpty()) {
				for (TestCase testCase : entry.getTestCaseList()) {
					if (testCase.getStepList() != null && !testCase.getStepList().isEmpty()) {
						for (Step step : testCase.getStepList()) {
							if (step.getID().equals(stepUID)) {
								if(step instanceof MySQLStep){
									((MySQLStep) step).setOperation(((MySQLStep)src).getOperation());
									step.setName(src.getName());
									step.setService(src.getService());
									step.setServer(src.getServer());
									step.setExecutionList(src.getExecutionList());
									step.setAssertList(src.getAssertList());
									step.setAssetList(src.getAssetList());
									step.setDependsOn(src.getDependsOn());
									step.setVariableList(src.getVariableList());
								}
								if(step instanceof MongoStep){
									((MongoStep) step).setAction(((MongoStep) src).getAction());
									step.setAssertList(src.getAssertList());
									step.setAssetList(src.getAssetList());
									((MongoStep) step).setCollection(((MongoStep) src).getCollection());
									step.setDependsOn(src.getDependsOn());
									step.setExecutionList(src.getExecutionList());
									step.setName(src.getName());
									((MongoStep) step).setQuery(((MongoStep) src).getQuery());
									step.setServer(src.getServer());
									step.setService(src.getService());
									step.setVariableList(src.getVariableList());
								}
								if(step instanceof SoapStep){
									step.setAssertList(src.getAssertList());
									step.setAssetList(src.getAssetList());
									step.setDependsOn(src.getDependsOn());
									step.setExecutionList(src.getExecutionList());
									step.setName(src.getName());
									((SoapStep) step).setRequest(((SoapStep) src).getRequest());
									step.setServer(src.getServer());
									step.setService(src.getService());
									step.setVariableList(src.getVariableList());
								}
								if(step instanceof RestStep){
									step.setAssertList(src.getAssertList());
									step.setAssetList(src.getAssetList());
									((RestStep) step).setContentType(((RestStep) src).getContentType());
									((RestStep) step).setCookie(((RestStep) src).getCookie());
									step.setDependsOn(src.getDependsOn());
									step.setExecutionList(src.getExecutionList());
									((RestStep) step).setHeader(((RestStep) src).getHeader());
									((RestStep) step).setMethod(((RestStep) src).getMethod());
									step.setName(src.getName());
									((RestStep) step).setPath(((RestStep) src).getPath());
									((RestStep) step).setQuery(((RestStep) src).getQuery());
									((RestStep) step).setRequest(((RestStep) src).getRequest());
									step.setServer(src.getServer());
									step.setService(src.getService());
									step.setVariableList(src.getVariableList());
								}
							}
						}
					}
				}
			}
		}
	}
	
	

	@Override
	public String toString() {
		return "TestProject [name=" + name + ", testSuiteList="
				+ toStringSuite(testSuiteList) + ", assetList=" + assetList
				+ ", environmentList=" + toStringEnv(environmentList)
				+ ", variableList=" + variableList + ", uuid=" + uuid + "]";
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
		result = prime * result
				+ ((assetList == null) ? 0 : assetList.hashCode());
		result = prime * result
				+ ((environmentList == null) ? 0 : environmentList.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((testSuiteList == null) ? 0 : testSuiteList.hashCode());
		result = prime * result
				+ ((variableList == null) ? 0 : variableList.hashCode());
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
