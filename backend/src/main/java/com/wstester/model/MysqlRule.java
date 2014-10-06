package com.wstester.model;

public class MysqlRule extends Rule {

	private static final long serialVersionUID = 1L;
	public enum InputType {OPERATION};
	private InputType inputType;
	
	public MysqlRule () {
		
	}
	public MysqlRule(InputType inputType, String input, String output){
		
		this.inputType = inputType;
		this.inputString = input;
		this.output = output;
	}
	
	@Override
	public String run(Step step) {

		if(step instanceof MySQLStep){
			if(inputType.equals(InputType.OPERATION)){
				if(inputString != null && ((MySQLStep)step).getOperation().equals(inputString)){
					return output;
				}
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "MysqlRule [inputType=" + inputType + ", inputString=" + inputString + ", inputAsset=" + inputAsset + ", output=" + output + "]";
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
		MysqlRule other = (MysqlRule) obj;
		if (inputType != other.inputType)
			return false;
		return true;
	}
}
