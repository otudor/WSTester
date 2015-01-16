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
		if(stepExecutionList == null) {
			stepExecutionList = new ArrayList<StepExecution>();
		}
	}
	
	@Override
	public void addStep(Step step) {
		
		log.info(step.getID(), "Adding step to step list: " + step.detailedToString());
		StepExecution stepExecution = alreadyContains(stepExecutionList, step);
		if(stepExecution != null) {
			log.info(step.getID(), "Updating the date of the execution");
			stepExecution.setLastRun(new Date());
		}
		else {
			stepExecution = new StepExecution(step, new Date());
			stepExecutionList.add(stepExecution);
			log.info(step.getID(), "Step list now contains " + stepExecutionList.size() + " step(s)");
		}
	}

	private StepExecution alreadyContains(List<StepExecution> stepExecutionList, Step step) {
		
		for(StepExecution stepExecution : stepExecutionList) {
			if(stepExecution.getStep().getID().equals(step.getID())) {
				log.info(step.getID(), "StepList already contains the step");
				return stepExecution;
			}
		}
		log.info(step.getID(), "StepList doesn't contain the step");
		return null;
	}

	@Override
	public Step getStep(String stepId) {
		
		for(StepExecution stepExecution : stepExecutionList) {
			Step step = stepExecution.getStep();
			if(step.getID().equals(stepId)) {
				
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
			if(step.getID().equals(stepId)) {
				return stepExecution.getLastRun();
			}
		}
		return null;
	}
}