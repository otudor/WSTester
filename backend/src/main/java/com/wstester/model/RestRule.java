package com.wstester.model;

import javax.xml.bind.annotation.XmlType;

public class RestRule extends Rule{

	private static final long serialVersionUID = 1L;
	@XmlType(name="restInputType")
	public enum InputType {PATH, METHOD};
	private InputType inputType;
	
	public RestRule(){
	}
	
	public RestRule(InputType inputType, String input, String output){
		
		this.inputType = inputType;
		this.inputString = input;
		this.output = output;
	}
	
	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	@Override
	public String run(Step step){
		
		if(step instanceof RestStep){
			
			if(inputType.equals(InputType.PATH)){
				if(inputString != null && ((RestStep)step).getPath().contains(inputString)){
					return output;
				}
			}
			
			if(inputType.equals(InputType.METHOD)){
				if(inputString != null && ((RestStep)step).getMethod().toString().equalsIgnoreCase(inputString)){
					return output;
				}
			}
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "RestRule [inputType=" + inputType + ", inputString=" + inputString + ", inputAsset=" + inputAsset + ", output=" + output + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((inputType == null) ? 0 : inputType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RestRule other = (RestRule) obj;
		if (inputType != other.inputType)
			return false;
		return true;
	}
}
