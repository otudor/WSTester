package com.wstester.services.definition;

import com.wstester.model.Step;
import com.wstester.services.common.Stateless;

@Stateless
public interface IStepManager extends IService{

	public void addStep(Step step);
	public Step getStep(String stepId);
	public Boolean hasDependant(String stepId);
}
