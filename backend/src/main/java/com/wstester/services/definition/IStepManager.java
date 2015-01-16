package com.wstester.services.definition;

import java.util.Date;

import com.wstester.model.Step;
import com.wstester.services.common.Stateful;

@Stateful
public interface IStepManager extends IService{

	public void addStep(Step step);
	public Step getStep(String stepId);
	public Boolean hasDependant(String stepId);
	public Date getLastRun(String stepId);
}
