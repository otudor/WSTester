package com.wstester.camel.unittest;

import static org.apache.camel.component.jms.JmsComponent.jmsComponentClientAcknowledge;

import java.util.HashMap;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.Mongo;
import com.wstester.dispatcher.ExchangeDelayer;
import com.wstester.dispatcher.mongo.MongoRoute;
import com.wstester.dispatcher.rest.RestRoute;
import com.wstester.model.Action;
import com.wstester.model.MongoService;
import com.wstester.model.MongoStep;
import com.wstester.model.Response;
import com.wstester.model.RestService;
import com.wstester.model.RestStep;
import com.wstester.model.Server;
import com.wstester.server.Main;

public class DependantStepsTest extends CamelTestSupport{

	private static HttpServer server;
	
    @BeforeClass
    public static void setUpRest() throws Exception {
        server = Main.startRestServer();
    }
 
    @AfterClass
    public static void tearDownRest() throws Exception {
        server.stop();
    }
    
    @Override
    protected CamelContext createCamelContext() throws Exception {
        CamelContext camelContext = super.createCamelContext();
        
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        camelContext.addComponent("jms", jmsComponentClientAcknowledge(connectionFactory));
        
        return camelContext;
    }
    
    @SuppressWarnings("deprecation")
	@Override
    protected JndiRegistry createRegistry() throws Exception {
    	JndiRegistry jndiRegistry = super.createRegistry();
    	
    	jndiRegistry.bind("mongoConnection", new Mongo());
    	return jndiRegistry;
    }
	
    @Override
    protected RouteBuilder[] createRouteBuilders() throws Exception {
    	MongoRoute mongoRoute = new MongoRoute();
    	mongoRoute.from("jms:topic:responseTopic").to("mock:response");
    	RestRoute restRoute = new RestRoute();
    	ExchangeDelayer delayer = new ExchangeDelayer();
    	
    	return new RouteBuilder[] {mongoRoute, restRoute, delayer};
    }
    
	@Test
	public void twoDependantTests() throws Exception{
		
		MockEndpoint resultEndpoint = getMockEndpoint("mock:response");
		resultEndpoint.expectedMessageCount(2);
		
		RestStep restStep = getRestStep();
		MongoStep mongoStep = getMongoStep();
		mongoStep.setDependsOn(restStep.getID());
		
		template.asyncSendBody("jms:mongoQueue", mongoStep);
		template.asyncSendBody("jms:restQueue", restStep);
		
		resultEndpoint.await();
		assertEquals(resultEndpoint.getReceivedExchanges().get(0).getIn().getBody(Response.class).getStepID(), restStep.getID());
		assertEquals(resultEndpoint.getReceivedExchanges().get(1).getIn().getBody(Response.class).getStepID(), mongoStep.getID());
		resultEndpoint.assertIsSatisfied();
	}
	
	@Test
	public void messagesInTheSameQueueDontWait() throws Exception{
		
		MockEndpoint resultEndpoint = getMockEndpoint("mock:response");
		
		RestStep restStep = getRestStep();
		MongoStep mongoStepDependant = getMongoStep();
		mongoStepDependant.setDependsOn(restStep.getID());
		MongoStep mongoStep = getMongoStep();
		
		template.asyncSendBody("jms:mongoQueue", mongoStepDependant);
		Thread.sleep(2000);
		template.asyncSendBody("jms:mongoQueue", mongoStep);
		
		resultEndpoint.expectedMessageCount(1);
		resultEndpoint.await();
		assertEquals(resultEndpoint.getReceivedExchanges().get(0).getIn().getBody(Response.class).getStepID(), mongoStep.getID());
		resultEndpoint.assertIsSatisfied();
		
		resultEndpoint.expectedMinimumMessageCount(2);
		template.asyncSendBody("jms:restQueue", restStep);
		resultEndpoint.await();
		assertEquals(resultEndpoint.getReceivedExchanges().get(1).getIn().getBody(Response.class).getStepID(), restStep.getID());
		assertEquals(resultEndpoint.getReceivedExchanges().get(2).getIn().getBody(Response.class).getStepID(), mongoStepDependant.getID());
		
		resultEndpoint.expectedMessageCount(3);
		resultEndpoint.assertIsSatisfied();
	}
	
	public Server getServer(){
		return new Server("Server", "localhost", "description");
	}
	
	public RestService getRestService() {
		RestService service = new RestService();
		service.setName("restService");
		service.setPort("9997");
		return service;
	}
	
	public MongoService getMongoService(){
		MongoService service = new MongoService();
		service.setName("Mongo Service");
		service.setPort("27017");
		service.setDbName("test");
		service.setUser("appuser");
		service.setPassword("apppass");
		return service;
	}
	
	public MongoStep getMongoStep(){
		MongoStep mongoStep = new MongoStep();
		mongoStep.setName("Step 2");
		mongoStep.setServer(getServer());
		mongoStep.setService(getMongoService());
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		query.put("name", "HAC");
		mongoStep.setAction(Action.SELECT);
		mongoStep.setCollection(collection);
		mongoStep.setQuery(query);
		return mongoStep;
	}
	
	public RestStep getRestStep(){
		RestStep step = new RestStep();
		step.setName("Step 1");
		step.setServer(getServer());
		step.setService(getRestService());
		step.setPath("/customer/getCustomers");
		step.setMethod("GET");
		return step;
	}
}
