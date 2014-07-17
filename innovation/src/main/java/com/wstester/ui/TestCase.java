package com.wstester.ui;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestCase {
	private List<Step> step;

	public List<Step> getStep() {
		return step;
	}

	public void setStep(List<Step> step) {
		this.step = step;
	}
}
