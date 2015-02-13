package com.wstester.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
	public void setVariableContent(String variableId, String content) {
		
		variableList.forEach(variable -> {
			if(variable.getId().equals(variableId)) {
				
				log.info(variableId, "Set content: " + content);
				variable.setContent(content);
			}
		});
	}

	@Override
	public String getVariableContent(String variableId) {
		
		List<Variable> filtered = variableList.stream().filter(variable -> variable.getId().equals(variableId)).collect(Collectors.toList());
		if(filtered.size() == 1) {
			return filtered.get(0).getContent();
		} else {
			return null;
		}
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
		List<Variable> filtered = variableList.stream().filter(variable -> variable.getId().equals(variableId)).collect(Collectors.toList());
		if(filtered.size() == 1) {
			return filtered.get(0);
		} else {
			return null;
		}
		
	}

	@Override
	public void allVariablesSent() {
		this.allStepsReceived = true;
	}
}
