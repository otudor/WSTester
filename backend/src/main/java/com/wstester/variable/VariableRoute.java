package com.wstester.variable;

import java.util.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import com.wstester.model.Variable;


public class VariableRoute extends RouteBuilder {

	private static Set<Variable> variableSet = new HashSet<Variable>();
	
	public void configure() throws Exception{
		 
		from("jms:variableQueue")
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {

				Variable variable = exchange.getIn().getBody(Variable.class);
				variableSet.add(variable);
			}
		});
		
		from("jms:topic:finishTopic")
		.log("[${body.getID}] received finish message")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				variableSet.clear();
				variableSet = null;
				variableSet = new HashSet<Variable>(); 
			}
		});
	}

	public Variable getVariable (String variableId) {

		for (Variable variable : variableSet){
				if(variable.getID().equals(variableId)){
					return variable;
				}
			}
				
		return null;
		
	}
	
	public static boolean allVariablesReceived(int variableSize) {
		
		if(variableSet.size() == variableSize){
			return true;
		}

		return false;
	}
}