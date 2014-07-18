package com.wstester.model;

import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestCase {

	private String name;
	private List<Step> stepList;

	private String uuid;

	public TestCase() {
		uuid = UUID.randomUUID().toString();
	}

	public String getID() {
		return this.uuid;
	}

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestCase other = (TestCase) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (stepList == null) {
			if (other.stepList != null)
				return false;
		} else if (!stepList.equals(other.stepList))
			return false;
		return true;
	}
}
