package com.wstester.ui;

import java.util.List;

public class TestCase {
	
	private String name;
	private List<Step> stepList;

	public List<Step> getStep() {
		return stepList;
	}

	public void setStep(List<Step> step) {
		this.stepList = step;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
