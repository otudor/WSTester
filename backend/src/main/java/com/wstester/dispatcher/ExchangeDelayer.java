package com.wstester.dispatcher;

import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.persistance.ResponseService;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.IStepManager;
import com.wstester.services.definition.IVariableManager;
import com.wstester.util.ProjectProperties;

public class ExchangeDelayer {

	private CustomLogger log = new CustomLogger(ExchangeDelayer.class);
	
	public boolean delayByVariable(String variableId) throws Exception{
		
		log.info(variableId, "Waiting for variable to be assigned");
		ProjectProperties properties = new ProjectProperties();
		Long timeout = properties.getLongProperty("stepFinishTimeout");
		IVariableManager variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		while((variableManager.getVariableContent(variableId)==null || variableManager.getVariableContent(variableId).isEmpty()) && timeout >= 0) {
			log.info(variableId,"Variable not assigned");
			timeout-=1000;
			Thread.sleep(1000);
		}
		
		if(variableManager.getVariableContent(variableId)==null || variableManager.getVariableContent(variableId).isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public void delayByStep(Step step) throws Exception{
		
		ProjectProperties properties = new ProjectProperties();
		Long timeout = properties.getLongProperty("stepFinishTimeout");
		
		if(step.getDependsOn() != null){
			while(!hasStepFinished(step.getDependsOn()) && timeout > 0){
				log.info(step.getId(),"Waiting to finish: " + step.getDependsOn());
				timeout-=1000;
				Thread.sleep(1000);
			}
		}
	}

	private Boolean hasStepFinished(String stepId) throws Exception{
		
		// get a instance of ResponseService to get the result from the H2 DB
		ICamelContextManager camelContextManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		ResponseService responseService = camelContextManager.getCamelContext().getBean("responseServiceImpl", ResponseService.class);
		
		// Get a instance of IStepManager to check the lastRunDate of the step
		IStepManager stepManager = ServiceLocator.getInstance().lookup(IStepManager.class);
		
		// verify if the step finished for the current run
		int expectedNumberOfResponses = stepManager.getNumberOfResponses(stepId);
		int finishedResponses = responseService.getNumberOfReceivedResponses(stepId, stepManager.getLastRun(stepId));
		
		if(expectedNumberOfResponses == finishedResponses) {
			return true;
		}
		else {
			return false;
		}
	}
}