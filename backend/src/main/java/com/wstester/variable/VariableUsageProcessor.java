package com.wstester.variable;

import javassist.NotFoundException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.w3c.dom.NodeList;

import com.wstester.dispatcher.ExchangeDelayer;
import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IVariableManager;

public class VariableUsageProcessor implements Processor {

	CustomLogger log = new CustomLogger(VariableUsageProcessor.class);
	IVariableManager variableManager;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		Step step = exchange.getIn().getBody(Step.class);
		log.info(step.getId(), "Replacing variables");
		
		String stepXML = VariableUtil.convertToXML(step);
		NodeList nodesWithVariables = VariableUtil.getNodesWithVariables(stepXML);
		
		for(int i=0; i<nodesWithVariables.getLength(); i++) {
			
			String nodeValue = nodesWithVariables.item(i).getFirstChild().getNodeValue();
			if(!nodeValue.trim().isEmpty()) {
				
				String variableName = nodeValue.substring(nodeValue.indexOf("${") + 2, nodeValue.indexOf("}"));
				Variable variable = variableManager.getVariableByName(variableName);
				
				if(variable != null) {
					if(variable.getContent() == null || variable.getContent().trim().isEmpty()) {
						
						log.info(step.getId(), "Waiting for variable: " + variable.getId());
						boolean succeeded = new ExchangeDelayer().delayByVariable(variable.getId());
						
						if(!succeeded) {
							log.error(step.getId(), "Variable with name: " + variableName + " has empty content!");
							throw new NotFoundException("Variable with name: " + variableName + " has empty content!");
						}
					}
					
					log.info(step.getId(), "Replacing variable " + variableName + " with " + variable.getContent());
					stepXML = stepXML.replace("${" + variableName + "}", variable.getContent());
					
				} else {
					log.error(step.getId(), "Variable with name: " + variableName + " was not found!");
					throw new NotFoundException("Variable with name: " + variableName + " was not found!");
				}
				
			}
		}
		
		Step stepWithoutVariables = VariableUtil.convertToStep(stepXML);
		exchange.getIn().setBody(stepWithoutVariables);
	}
}