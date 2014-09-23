package com.wstester.services.definition;

import java.util.List;

import com.wstester.model.Variable;

public interface IVariableManager {

	void setVariableContent(Variable variable, String content);
	
	String getVariableContent(Variable variable);
	
	List<Variable> getAllVariables();
}
