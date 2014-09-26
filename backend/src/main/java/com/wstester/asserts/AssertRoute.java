package com.wstester.asserts;
import java.util.HashSet;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import com.wstester.model.Step;

public class AssertRoute extends RouteBuilder {

	private HashSet<Step> stepList = new HashSet<Step>();
	
	@Override
	public void configure() throws Exception {
		
		interceptSendToEndpoint("jms:topic:responseTopic")
		.log("[${body.getStepID}] Response before running asserts: ${body}")
		.process(new AssertProcessor(stepList));
		
		from("jms:assertQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				Step step = exchange.getIn().getBody(Step.class);
				stepList.add(step);
			}
		});
	}

}
