package com.wstester.dispatcher;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import com.wstester.model.Response;
import com.wstester.model.RestService;
import com.wstester.model.RestStep;
import com.wstester.model.Server;

public class RestRoute extends RouteBuilder{

	private RestStep step = null;
	
	
	@Override
	public void configure() throws Exception {
		
		from("jms:restQueue")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				step = exchange.getIn().getBody(RestStep.class);
		
				exchange.getIn().setBody(step.getBody());
				exchange.getIn().setHeader(Exchange.HTTP_URI, getURI(step));
				exchange.getIn().setHeader(Exchange.HTTP_QUERY, step.getQuery());
				exchange.getIn().setHeader(Exchange.HTTP_PATH, step.getPath());
				exchange.getIn().setHeader(Exchange.HTTP_METHOD, step.getMethod());
				exchange.getIn().setHeader("name", step.getHeader());
				exchange.getIn().setHeader("Cookie", "name" + "=" + step.getCookie());
			}
		})
		.recipientList(simple("http://none.none"))
		.process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {

				Message in = exchange.getIn();
				System.out.println(in.getHeader(Exchange.HTTP_RESPONSE_CODE, Integer.class));
				
				Response response = new Response();
				response.setStepID(step.getID());
				response.setContent(in.getBody(String.class));
				response.setPass(true);

				
				exchange.getIn().setBody(response);
			}
		})
		.to("jms:topic:responseTopic");
	}
	
	private Object getURI(RestStep step) {
		
		Server server = step.getServer();
		RestService service = (RestService) step.getService();
		
		return"http://" + server.getIp() + ":" + service.getPort();
	}
}
