package com.wstester.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mongodb.MongoDbConstants;
import com.wstester.model.MongoService;
import com.wstester.model.MongoStep;
import com.wstester.model.Response;

public class MongoRoute extends RouteBuilder {

	private MongoStep step = null;
	
	@Override
	public void configure() throws Exception {
		
		from("jms:mongoQueue?concurrentConsumers=20&asyncConsumer=true")
		.bean(ExchangeDelayer.class, "delay")
		.bean(MongoConnection.class, "setConnectionBean")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				step = exchange.getIn().getBody(MongoStep.class);
				MongoService service = (MongoService) step.getService();
				
				String operation = "";
				switch (step.getAction()) {
					case SELECT:{
						operation = "findAll";
						break;
					}
					case INSERT:{
						operation = "insert";
						break;
					}
				}
				
				exchange.getIn().setHeader(MongoDbConstants.DATABASE, service.getDbName());
				exchange.getIn().setHeader(MongoDbConstants.COLLECTION, step.getCollection());
				exchange.getIn().setHeader(MongoDbConstants.OPERATION_HEADER, operation);
				exchange.getIn().setBody(step.getQuery());
				
			}
		})
		.dynamicRouter(method(MongoConnection.class, "getConnection"))
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {

				Message in = exchange.getIn();
				
				Response response = new Response();
				response.setStepID(step.getID());
				response.setContent(in.getBody(String.class));
				response.setPass(true);

				exchange.getIn().setBody(response);;
			}
		})
		.to("jms:topic:responseTopic");
		
	}
}
