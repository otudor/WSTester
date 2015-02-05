package com.wstester.dispatcher;

import org.springframework.context.support.AbstractXmlApplicationContext;

import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.persistance.ResponseService;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.IStepManager;
import com.wstester.util.ProjectProperties;

public class ExchangeDelayer {

	private CustomLogger log = new CustomLogger(ExchangeDelayer.class);
	
	public void delay(Step step) throws Exception{
		
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
		AbstractXmlApplicationContext camelContext = camelContextManager.getCamelContext();
		ResponseService responseService = camelContext.getBean("responseServiceImpl", ResponseService.class);
		
		// Get a instance of IStepManager to check the lastRunDate of the step
		IStepManager stepManager = ServiceLocator.getInstance().lookup(IStepManager.class);
		
		// verify if the step finished for the current run
		return responseService.hasStepFinished(stepId, stepManager.getLastRun(stepId));
	}
}