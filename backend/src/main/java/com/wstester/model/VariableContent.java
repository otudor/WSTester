package com.wstester.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class VariableContent extends RouteBuilder {
 
	private static Set<Variable> variableSet = new HashSet<Variable>() ;
	private static int contentResponses = 0;
	
	@Override
	public void configure() throws Exception {
		
		from("jms:variableQueue")
		.choice()
		.when(body().contains("content"))
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				Variable variable = exchange.getIn().getBody(Variable.class);
				variableSet.add(variable);
				contentResponses++;
			}
		}).endChoice();
	}
	public static Variable getContent(String variableID){
		
			for (Variable variable : variableSet){
				if (variable.getID().equals(variableID)){
					if(variable.getContent()== null){
						
						try {
								
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					variableSet.remove(variable);
					return variable;
				}
			}
		return null;
	}
public static boolean allContentResponsesReceived(int size){
		
		if(contentResponses == size) {
			return true;
		}
		
		return false;
	}
}