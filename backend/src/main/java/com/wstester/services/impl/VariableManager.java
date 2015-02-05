package com.wstester.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.wstester.log.CustomLogger;
import com.wstester.model.Variable;
import com.wstester.services.definition.IVariableManager;

public class VariableManager implements IVariableManager {

	private CustomLogger log = new CustomLogger(VariableManager.class);
	private List<Variable> variableList;
	private Boolean allStepsReceived;
	
	public VariableManager(){
		variableList = new ArrayList<Variable>();
		allStepsReceived = false;
	}
	
	@Override
	public void addVariable(Variable variable) {
		log.info(variable.getId(), "Adding variable to the variableList: " + variable.toString());
		variableList.add(variable);
	}
	
	@Override
	public void setVariableContent(Variable variable, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getVariableContent(Variable variable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Variable> getAllVariables() {
		return variableList;
	}

	@Override
	public Variable getVariable(String variableId) throws InterruptedException {
		
		while(!allStepsReceived){
			Thread.sleep(1000l);
		}
		for(Variable variable : variableList) {
			if(variable.getId().equals(variableId)) {
				return variable;
			}
		}
		return null;
	}

	@Override
	public void allVariablesSent() {
		this.allStepsReceived = true;
	}
}
