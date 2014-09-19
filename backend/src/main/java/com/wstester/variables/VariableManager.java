package com.wstester.variables;

import java.util.*;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import com.wstester.model.Variable;


public class VariableManager extends RouteBuilder {

	private static Set<Variable> variableSet = new HashSet<Variable>() ;
	public void configure() throws Exception{
		 
		from("jms:variableQueue")
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {

				Variable variable = exchange.getIn().getBody(Variable.class);
				variableSet.add(variable);
				
			}
		});
	}

	public static Variable getVariable (String variableId) {

		for (Variable variable : variableSet){
				if(variable.getID().equals(variableId)){
					variableSet.remove(variable);
					return variable;
				}
			}
				
		return null;
		
	}
	public static boolean allVariablesReceived(int variableSize) {
		
		return true;
	}

}