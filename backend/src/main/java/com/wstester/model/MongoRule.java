package com.wstester.model;

import java.util.Map;

public class MongoRule extends Rule {

	private static final long serialVersionUID = 1L;
	public enum InputType {QUERY, COLLECTION};
	private InputType inputType;
	private Map<String, String> inputQuery;
	
	public MongoRule(){
	}
	
	public MongoRule(InputType inputType, String input, String output){
		
		this.inputType = inputType;
		this.inputString = input;
		this.output = output;
	}
	
	public MongoRule(InputType inputType, Map<String, String> input, String output){
		
		this.inputType = inputType;
		this.inputQuery = input;
		this.output = output;
	}
	
	@Override
	public String run(Step step) {
		
		if(step instanceof MongoStep){
			if(inputType.equals(InputType.QUERY)){
				if(inputQuery != null && ((MongoStep) step).getQuery().equals(inputQuery)){
					return output;
				}
			}
			else if(inputType.equals(InputType.COLLECTION)){
				if(inputString != null && ((MongoStep) step).getCollection().equals(inputString)){
					return output;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "MongoRule [inputType=" + inputType + ", inputQuery=" + inputQuery + ", inputString=" + inputString + ", output=" + output + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((inputQuery == null) ? 0 : inputQuery.hashCode());
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
		MongoRule other = (MongoRule) obj;
		if (inputQuery == null) {
			if (other.inputQuery != null)
				return false;
		} else if (!inputQuery.equals(other.inputQuery))
			return false;
		if (inputType != other.inputType)
			return false;
		return true;
	}
}
