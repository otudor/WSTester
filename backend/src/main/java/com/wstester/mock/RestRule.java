package com.wstester.mock;

import com.wstester.model.RestStep;
import com.wstester.model.Step;

public class RestRule implements Rule{

	private String inputType;
	private String input;
	private String output;
	
	public RestRule(String inputType, String input, String output){
		
		this.inputType = inputType;
		this.input = input;
		this.output = output;
	}
	
	@Override
	public String run(Step step){
		
		if(step instanceof RestStep){
			
			if(inputType.equals("path")){
				if(((RestStep)step).getPath().contains(input)){
					return output;
				}
			}
			
			if(inputType.equals("method")){
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
}
