package com.wstester.dispatcher;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

import com.wstester.client.Client;
import com.wstester.client.mongo.MongoDBClient;
import com.wstester.client.mysql.MySQLClient;
import com.wstester.client.rest.RestClient;
import com.wstester.client.soap.SOAPClient;
import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.RestStep;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;

public class DispatcherImpl implements Dispatcher, ApplicationEventPublisherAware{

	private ApplicationEventPublisher publisher;
	
	public void dispatch(Step step, Client client) {
		
		if(step instanceof MongoStep){
			dispatch((MongoStep)step, (MongoDBClient) client);
		}
		else if(step instanceof RestStep){
			dispatch((RestStep)step, (RestClient) client);
		}
		else if(step instanceof MySQLStep){
			dispatch((MySQLStep)step, (MySQLClient) client);
		}
		else if(step instanceof SoapStep){
			dispatch((SoapStep)step, (SOAPClient) client);
		}
	}
	
	public void dispatch(MongoStep step, MongoDBClient client){
		System.out.println("Mongo Step Name: " + step.getName() + " with client: " + client.getID());
		
		
		StepRunEvent event = new StepRunEvent(this);
		Response response = new Response();
		response.setStepID(step.getID());
		response.setContent((client.select(step.getCollection(), step.getQuery())).toString());
		response.setPass(true);
		event.setResponse(response);
		
		publisher.publishEvent(event);
	}
	
	public void dispatch(RestStep step, RestClient client){
		System.out.println("Rest Step Name: " + step.getName() + " with client: " + client.getID());
	}
	
	public void dispatch(MySQLStep step, MySQLClient client){
		System.out.println("MySQL Step Name: " + step.getName() + " with client: " + client.getID());
		
	}
	
	public void dispatch(SoapStep step, SOAPClient client){
		System.out.println("SOAP Step Name: " + step.getName() + " with client: " + client.getID());
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
		this.publisher = publisher;
	}
}
