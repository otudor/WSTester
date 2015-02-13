package com.wstester.services.definition;

import java.util.List;

import com.wstester.model.Variable;
import com.wstester.services.common.Stateful;

@Stateful
public interface IVariableManager extends IService{

	void addVariable(Variable variable);
	void setVariableContent(String variableId, String content);
	String getVariableContent(String variableId);
	Variable getVariable(String variableId) throws InterruptedException;
	List<Variable> getAllVariables();
	void allVariablesSent();
}
