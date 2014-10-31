package com.wstester.model;

import java.util.Map;
import javax.xml.bind.annotation.XmlType;

public class MongoRule extends Rule {

	private static final long serialVersionUID = 1L;
	@XmlType(name="mongoInputType")
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
	
	public InputType getInputType() {
		return inputType;
	}

	public void setInputType(InputType inputType) {
		this.inputType = inputType;
	}

	public Map<String, String> getInputQuery() {
		return inputQuery;
	}

	public void setInputQuery(Map<String, String> inputQuery) {
		this.inputQuery = inputQuery;
	}

	@Override
	protected Object getStepInput(Step step) {
		
		if(step instanceof MongoStep){
			if(inputType.equals(InputType.QUERY)){
				return ((MongoStep) step).getQuery();
			}
			
			else if(inputType.equals(InputType.COLLECTION)){
				return ((MongoStep) step).getCollection();
			}
		}
		return null;
	}
	
	@Override
	protected Object getRuleInput() {
		
		if(inputQuery != null){
			return inputQuery;
		}
		else{
			return super.getRuleInput();
		}
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
