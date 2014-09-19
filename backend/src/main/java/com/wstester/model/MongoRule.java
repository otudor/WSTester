package com.wstester.model;

public class MongoRule extends Rule {

	private static final long serialVersionUID = 1L;
	
	public enum InputType {QUERY};
	private InputType inputType;
	
	public MongoRule(InputType inputType, String input, String output){
		
		this.inputType = inputType;
		this.inputString = input;
		this.output = output;
	}
	
	@Override
	public String run(Step step) {
		
		if(step instanceof MongoStep){
			if(inputType.equals(InputType.QUERY)){
				if(inputString != null && ((MongoStep) step).getQuery().equals(inputString)){
					return output;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "MongoRule [inputType=" + inputType + ", inputString=" + inputString + ", inputAsset=" + inputAsset + ", output=" + output + "]";
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
		MongoRule other = (MongoRule) obj;
		if (inputType != other.inputType)
			return false;
		return true;
	}
}
