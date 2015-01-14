package com.wstester.services.impl;

import java.util.ArrayList;
import java.util.List;

import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.services.definition.IStepManager;

public class StepManager implements IStepManager {

	private CustomLogger log = new CustomLogger(StepManager.class);
	private static List<Step> stepList;
	
	public StepManager() {
	}
	
	public StepManager(Boolean newList) {
		if(newList) {
			stepList = new ArrayList<Step>();
		}
	}
	
	@Override
	public void addStep(Step step) {
		
		log.info(step.getID(), "Added step to step list: " + step.detailedToString());
		stepList.add(step);
		log.info(step.getID(), "Step list now contains " + stepList.size() + " step(s)");
	}

	@Override
	public Step getStep(String stepId) {
		
		for(Step step : stepList) {
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
		for(Step step : stepList) {
			if(step.getDependsOn() != null && step.getDependsOn().equals(stepId)) {
				hasDependant = true;
			}
		}
		
		return hasDependant;
	}
}