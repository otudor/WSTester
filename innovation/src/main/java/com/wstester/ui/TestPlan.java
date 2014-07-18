package com.wstester.ui;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestPlan {
	
	private List<TestSuite> testSuiteList;
	private List<Asset> assetList;
	private List<Environment> environmentList;
	private String name;
	
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
}
