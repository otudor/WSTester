package com.wstester.model;

import java.util.HashSet;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import com.wstester.exceptions.WsException;

public class VariableContent extends RouteBuilder {
 
	private static Set<Variable> varSet = new HashSet<Variable>() ;
	private static int contentResponses = 0;
	
	@Override
	public void configure() throws Exception {
		
		from("jms:variableQueue")
		.choice()
		.when(body().contains("content"))
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				System.out.println("intra");
				Variable variable = exchange.getIn().getBody(Variable.class);
				varSet.add(variable);
				contentResponses++;
			}
		});
	}
	public static Variable getContent(String variableID) throws WsException{
		
			for (Variable variable : varSet){
				if (variable.getID().equals(variableID)){
					if(variable.getContent()== null){
						throw new WsException("Variable content is null....please define a variable content", null);
					
					}
					varSet.remove(variable);
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