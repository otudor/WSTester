package com.wstester.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import com.wstester.asset.AssetProcessor;
import com.wstester.model.Response;
import com.wstester.model.RestService;
import com.wstester.model.RestStep;
import com.wstester.model.Server;
import com.wstester.model.Step;

public class RestRoute extends RouteBuilder{

	@Override
	public void configure() throws Exception {
		
		from("jms:restQueue?concurrentConsumers=20&asyncConsumer=true")
		.log(LoggingLevel.INFO, "[${body.getID}] Received message on the rest queue")
		.log(LoggingLevel.INFO, "Delaying message bla bla")
		.bean(ExchangeDelayer.class, "delay")
		.log(LoggingLevel.INFO, "Processing ${id}  Me Got ${body.getID}")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				RestStep step = exchange.getIn().getBody(RestStep.class);
				exchange.setProperty("step", step);
				
				exchange.getIn().setBody(step.getRequest());
				exchange.getIn().setHeader(Exchange.HTTP_URI, getURI(step));
				exchange.getIn().setHeader(Exchange.HTTP_PATH, step.getPath());
				exchange.getIn().setHeader(Exchange.HTTP_METHOD, step.getMethod());
				exchange.getIn().setHeader(Exchange.CONTENT_TYPE, step.getContentType());
				
				if(step.getQuery() != null)	{
					for(String key : step.getQuery().keySet()){
						exchange.getIn().setHeader(Exchange.HTTP_QUERY, key + "=" + step.getQuery().get(key));
					}
				}
			
				if(step.getHeader() != null) {
					for(String key : step.getHeader().keySet()){
						exchange.getIn().setHeader(key, step.getHeader().get(key));
					}
				}	
			
				if(step.getCookie() != null) {
					for(String key : step.getCookie().keySet()){
						exchange.getIn().setHeader("Cookie", key + "=" + step.getCookie().get(key));
					}
				}
			}
		})
		.process(new AssetProcessor())
		.recipientList(simple("http://none.none"))
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {

				Step step = exchange.getProperty("step", Step.class);
				Message in = exchange.getIn();
				System.out.println(in.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class));
				
				Response response = new Response();
				response.setStepID(step.getID());
				response.setContent(in.getBody(String.class));
				response.setPass(true);
				
				exchange.getIn().setBody(response);
			}
		})
		.to("log:com.mycompany.order?showAll=true&multiline=true")
		.to("jms:topic:responseTopic");
	}
	
	
	
	private Object getURI(RestStep step) {
		
		Server server = step.getServer();
		RestService service = (RestService) step.getService();
		
		return"http://" + server.getIp() + ":" + service.getPort();
	}
}
