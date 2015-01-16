package com.wstester.services.definition;

import java.util.List;

import com.wstester.model.Variable;
import com.wstester.services.common.Stateless;

@Stateless
public interface IVariableManager extends IService{

	void setVariableContent(Variable variable, String content);
	
	String getVariableContent(Variable variable);
	
	List<Variable> getAllVariables();
}
