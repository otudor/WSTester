package com.wstester.mock;

import java.util.ArrayList;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.log.CustomLogger;
import com.wstester.model.Response;
import com.wstester.model.Step;

public class MockSystem implements Processor{

	private static ArrayList<Rule> ruleList = new ArrayList<Rule>();
	private CustomLogger log = new CustomLogger(MockSystem.class);		
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Step step = exchange.getIn().getBody(Step.class);
		
		log.info(step.getID(), "running mockSystem to return result");
		String responseContent = runRules(step);
		
		Response response = new Response();
		response.setStepID(step.getID());
		response.setContent(responseContent);
		response.setPass(true);

		exchange.getIn().setBody(response);
	}
	
	public void addRule(Rule rule){
		
		ruleList.add(rule);
		log.info("Adding rule: " + rule);
	}
	
	private String runRules(Step step){
		
		log.info(step.getID(), "Running mocking rules");
		String response = null;
		
		for(Rule rule : ruleList){
			response = rule.run(step);
			if(response != null){
				log.info(step.getID(), "Found rule: " + rule);
				break;
			}
		}
		
		return response;
	}
}
