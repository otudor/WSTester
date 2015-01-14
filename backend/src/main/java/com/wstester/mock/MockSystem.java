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
import com.wstester.model.Step;

public class MockSystem implements Processor{

	private List<Rule> ruleList;
	private CustomLogger log = new CustomLogger(MockSystem.class);		
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		ruleList = new ArrayList<Rule>();
		Step step = exchange.getProperty("step", Step.class);
		addRules(step.getService().getRuleList());

		
		log.info(step.getID(), "running mockSystem to return result");
		String responseContent = runRules(step);
		
		Response response = new Response();
		response.setRunDate(new Date());
		response.setStepId(step.getID());
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
		
		log.info(step.getID(), "Running mock rules");
		String response = null;
		
		for(Rule rule : this.ruleList){
			response = rule.run(step);
			if(response != null){
				log.info(step.getID(), "Found rule: " + rule);
				break;
			}
		}
		
		if(response == null) {
			if(ruleList.size() == 0){
				response = "No rules were defined for the service although the status of the service is MOCKED!";
				log.info(step.getID(), "No rules were defined for the service although the status of the service is MOCKED!");
			}
			else{
				response = "No rule was found to match this request";
				log.info(step.getID(), "No rule was found to match this request");
			}
		}
		
		return response;
	}
}
