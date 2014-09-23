package com.wstester.mock;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.log.CustomLogger;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Rule;
import com.wstester.model.Step;

public class MockSystem implements Processor{

	private ArrayList<Rule> ruleList;
	private CustomLogger log = new CustomLogger(MockSystem.class);		
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		ruleList = new ArrayList<Rule>();
		Step step = exchange.getIn().getBody(Step.class);
		addRules(step.getService().getRuleList());

		
		log.info(step.getID(), "running mockSystem to return result");
		String responseContent = runRules(step);
		
		Response response = new Response();
		response.setStepID(step.getID());
		response.setContent(responseContent);
		response.setStatus(ExecutionStatus.PASSED);

		exchange.getIn().setBody(response);
	}
	
	private void addRules(ArrayList<Rule> ruleList){
		
		for(Rule rule : ruleList){
			this.ruleList.add(rule);
			log.info("Adding rule: " + rule);
		}
	}
	
	private String runRules(Step step){
		
		log.info(step.getID(), "Running mocking rules");
		String response = null;
		
		for(Rule rule : this.ruleList){
			response = rule.run(step);
			if(response != null){
				log.info(step.getID(), "Found rule: " + rule);
				break;
			}
		}
		
		return response;
	}
}
