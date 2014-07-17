package com.wstester.ui;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestPlan {
	private List<TestSuite> testSuite;

	public List<TestSuite> getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(List<TestSuite> testSuite) {
		this.testSuite = testSuite;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	private List<Asset> assets;
}
