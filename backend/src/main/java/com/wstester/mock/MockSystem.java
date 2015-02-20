package com.wstester.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.log.CustomLogger;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Rule;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class MockSystem implements Processor{

	private List<Rule> ruleList;
	private CustomLogger log = new CustomLogger(MockSystem.class);		
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		ruleList = new ArrayList<Rule>();
		Step step = exchange.getIn().getBody(Step.class);
		log.info(step.getId(), "Received step on mock queue: " + step.detailedToString());
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		// this is done because the step is modified when the marshaling and unmarshaling was done in the variable processor
		// if the step has the request in the form of an xml then the xml is changed(some spaces and line feeds are missing)
		step = projectFinder.getStepById(step.getId());
		Service service = projectFinder.getServiceById(step.getServiceId());
		addRules(service.getRuleList());

		
		log.info(step.getId(), "running mockSystem to return result");
		String responseContent = runRules(step);
		
		Response response = new Response();
		response.setRunDate(new Date());
		response.setStepId(step.getId());
		response.setContent(responseContent);
		response.setStatus(ExecutionStatus.PASSED);

		exchange.getIn().setBody(response);
	}
	
	private void addRules(List<Rule> ruleList){
		
		if(ruleList != null){
			for(Rule rule : ruleList){
				this.ruleList.add(rule);
				log.info("Adding rule: " + rule);
			}
		}
		else{
			log.info("No rules were added because the ruleList is null");
		}
	}
	
	private String runRules(Step step){
		
		log.info(step.getId(), "Running mock rules");
		String response = null;
		
		for(Rule rule : this.ruleList){
			response = rule.run(step);
			if(response != null){
				log.info(step.getId(), "Found rule: " + rule);
				break;
			}
		}
		
		if(response == null) {
			if(ruleList.size() == 0){
				response = "No rules were defined for the service although the status of the service is MOCKED!";
				log.info(step.getId(), "No rules were defined for the service although the status of the service is MOCKED!");
			}
			else{
				response = "No rule was found to match this request";
				log.info(step.getId(), "No rule was found to match this request");
			}
		}
		
		return response;
	}
}
