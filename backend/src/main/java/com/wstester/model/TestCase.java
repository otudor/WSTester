package com.wstester.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestCase implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name;
	private List<Step> stepList;
	private String uuid;

	public TestCase() {
		uuid = UUID.randomUUID().toString();
		stepList = new ArrayList<Step>();
	}

	public String getId() {
		return this.uuid;
	}

	public void setId(String id) {
		this.uuid = id;
	}
	
	public List<Step> getStepList() {
		return stepList;
	}

	public void setStepList(List<Step> stepList) {
		this.stepList = stepList;
	}
	
	public void addStep(Step step){
		if(this.stepList == null){
			this.stepList = new ArrayList<Step>();
		}
		
		this.stepList.add(step);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public String detailedToString() {
		return "TestCase [name=" + name + ", stepList=" + toString(stepList) + ", uuid=" + uuid + "]";
	}

	private String toString(List<Step> stepList) {

		if(stepList == null){
			return "null";
		}
		
		StringBuilder sb = new StringBuilder("[");
		for(Step step : stepList){
			if(stepList.indexOf(step) != 0){
				sb.append(", ");
			}
			sb.append(step.detailedToString());
		}
		sb.append("]");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((stepList == null) ? 0 : stepList.hashCode());
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
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
