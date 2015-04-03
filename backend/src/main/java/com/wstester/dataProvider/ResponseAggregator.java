package com.wstester.dataProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.wstester.dispatcher.GeneralExceptionRoute;
import com.wstester.log.CustomLogger;
import com.wstester.model.Response;

public class ResponseAggregator extends GeneralExceptionRoute{

	private CustomLogger log = new CustomLogger(ResponseAggregator.class);
	static Map<String, List<Response>> queue = Collections.synchronizedMap(new HashMap<String, List<Response>>());
	
	@Override
	public void configure() throws Exception {
		super.configure();
		
		// intercept all the messages sent to variableQueue in order to sequence the responses for the same step 
		interceptSendToEndpoint("jms:variableQueue")
		.when(exchangeProperty("processed").isNull())
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				Response response = exchange.getIn().getBody(Response.class);
				
				if(queue.keySet() == null || queue.get(response.getStepId()) == null) {
					List<Response> responseList = Collections.synchronizedList(new ArrayList<Response>());
					responseList.add(response);
					queue.put(response.getStepId(), responseList);
					exchange.setProperty("skip", false);
					log.info(response.getStepId(), "There is no response in the aggregator. The step will continue to be processed");
				}
				else if(queue.get(response.getStepId()).isEmpty()) {
					queue.get(response.getStepId()).add(response);
					exchange.setProperty("skip", false);
					log.info(response.getStepId(), "There is no other response in the aggregator. The step will continue to be processed");
				}
				else {
					queue.get(response.getStepId()).add(response);
					exchange.setProperty("skip", true);
					log.info(response.getStepId(), "There are other responses in the aggregator. The step will NOT continue to be processed");
				}
			}
		})
		.choice()
			.when(exchangeProperty("skip").isEqualTo(true)).stop();
	
		
		// get the responses that have finish the processing
		from("jms:topic:responseTopic")
		.process(new Processor() {
			
			@Override
			public void process(Exchange exchange) throws Exception {
				
				Response response = exchange.getIn().getBody(Response.class);
				
				List<Response> responseListInQueue = Collections.synchronizedList(queue.get(response.getStepId()));
				
				// remove from the queue the response received			
				responseListInQueue.remove(0);
				
				// if there are more responses to send the send the first one
				if(!responseListInQueue.isEmpty()) {
					
					Response nextResponse = responseListInQueue.get(0);
					exchange.getIn().setBody(nextResponse);
					exchange.setProperty("processed", true);
					log.info(response.getStepId(), "The step finished processing. Sending next response: " + nextResponse);
				}
				else {
					exchange.getIn().setBody(null);
					log.info(response.getStepId(), "No other step is present in the aggregator. No step will be sent next");
				}
			}
		})
		.choice()
			.when(body().isNotNull()).to("jms:variableQueue");
	}
}
