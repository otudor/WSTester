package com.wstester.model;

import java.io.Serializable;

public class RestRule implements Rule, Serializable{

	public enum InputType {PATH, METHOD};
	
	private static final long serialVersionUID = 1L;
	private InputType inputType;
	private String input;
	private String output;
	
	public RestRule(InputType inputType, String input, String output){
		
		this.inputType = inputType;
		this.input = input;
		this.output = output;
	}
	
	@Override
	public String run(Step step){
		
		if(step instanceof RestStep){
			
			if(inputType.equals(InputType.PATH)){
				if(((RestStep)step).getPath().contains(input)){
					return output;
				}
			}
			
			if(inputType.equals(InputType.METHOD)){
				if(((RestStep)step).getMethod().equals(input)){
					return output;
				}
			}
		}
		
		return null;
	}

	@Override
	public String toString() {
		return "RestRule [inputType=" + inputType + ", input=" + input + ", output=" + output + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((input == null) ? 0 : input.hashCode());
		result = prime * result + ((inputType == null) ? 0 : inputType.hashCode());
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
		RestRule other = (RestRule) obj;
		if (input == null) {
			if (other.input != null)
				return false;
		} else if (!input.equals(other.input))
			return false;
		if (inputType == null) {
			if (other.inputType != null)
				return false;
		} else if (!inputType.equals(other.inputType))
			return false;
		if (output == null) {
			if (other.output != null)
				return false;
		} else if (!output.equals(other.output))
			return false;
		return true;
	}
}
