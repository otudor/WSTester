package com.wstester.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlTransient;

@XmlTransient
@XmlSeeAlso({RestRule.class, SoapRule.class, MongoRule.class, MysqlRule.class})
public abstract class Rule implements Serializable{

	private static final long serialVersionUID = 1L;
	protected String inputString;
	protected Asset inputAsset;
	protected String output;
	
	public abstract String run(Step step);

	public String getInputString() {
		return inputString;
	}

	public void setInputString(String inputString) {
		this.inputString = inputString;
	}

	public Asset getInputAsset() {
		return inputAsset;
	}

	public void setInputAsset(Asset inputAsset) {
		this.inputAsset = inputAsset;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((inputAsset == null) ? 0 : inputAsset.hashCode());
		result = prime * result + ((inputString == null) ? 0 : inputString.hashCode());
		result = prime * result + ((output == null) ? 0 : output.hashCode());
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
		Rule other = (Rule) obj;
		if (inputAsset == null) {
			if (other.inputAsset != null)
				return false;
		} else if (!inputAsset.equals(other.inputAsset))
			return false;
		if (inputString == null) {
			if (other.inputString != null)
				return false;
		} else if (!inputString.equals(other.inputString))
			return false;
		if (output == null) {
			if (other.output != null)
				return false;
		} else if (!output.equals(other.output))
			return false;
		return true;
	}
}