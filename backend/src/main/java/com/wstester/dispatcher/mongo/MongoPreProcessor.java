package com.wstester.dispatcher.mongo;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mongodb.MongoDbConstants;

import com.wstester.model.MongoService;
import com.wstester.model.MongoStep;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class MongoPreProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		
		MongoStep step = exchange.getIn().getBody(MongoStep.class);
		exchange.setProperty("step", step);
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		MongoService service = (MongoService) projectFinder.getServiceById(step.getServiceId());		
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

}
