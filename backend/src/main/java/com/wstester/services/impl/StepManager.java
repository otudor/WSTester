package com.wstester.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.model.StepExecution;
import com.wstester.services.definition.IStepManager;

public class StepManager implements IStepManager {

	private CustomLogger log = new CustomLogger(StepManager.class);
	private static List<StepExecution> stepExecutionList;
	
	public StepManager() {
		stepExecutionList = new ArrayList<StepExecution>();
	}
	
	@Override
	public void addStep(Step step) {
		
		log.info(step.getId(), "Adding step to step list: " + step.detailedToString());
		StepExecution stepExecution = alreadyContains(stepExecutionList, step);
		if(stepExecution != null) {
			log.info(step.getId(), "Updating the date of the execution");
			stepExecution.setLastRun(new Date());
		}
		else {
			stepExecution = new StepExecution(step, new Date());
			stepExecutionList.add(stepExecution);
			log.info(step.getId(), "Step list now contains " + stepExecutionList.size() + " step(s)");
		}
	}

	private StepExecution alreadyContains(List<StepExecution> stepExecutionList, Step step) {
		
		for(StepExecution stepExecution : stepExecutionList) {
			if(stepExecution.getStep().getId().equals(step.getId())) {
				log.info(step.getId(), "StepList already contains the step");
				return stepExecution;
			}
		}
		log.info(step.getId(), "StepList doesn't contain the step");
		return null;
	}

	@Override
	public Step getStep(String stepId) {
		
		for(StepExecution stepExecution : stepExecutionList) {
			Step step = stepExecution.getStep();
			if(step.getId().equals(stepId)) {
				
				log.info(stepId, "Found step in step list");
				return step;
			}
		}
		
		log.info(stepId, "Step not found in step list");
		return null;
	}

	@Override
	public Boolean hasDependant(String stepId) {
		
		Boolean hasDependant = false;
		for(StepExecution stepExecution : stepExecutionList) {
			Step step = stepExecution.getStep();
			if(step.getDependsOn() != null && step.getDependsOn().equals(stepId)) {
				hasDependant = true;
			}
		}
		
		return hasDependant;
	}

	@Override
	public Date getLastRun(String stepId) {
		
		for(StepExecution stepExecution : stepExecutionList) {
			Step step = stepExecution.getStep();
			if(step.getId().equals(stepId)) {
				return stepExecution.getLastRun();
			}
		}
		return null;
	}
}