package com.wstester.camel.unittest;

import static org.apache.camel.component.jms.JmsComponent.jmsComponentClientAcknowledge;

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
import com.wstester.model.MongoStep;
import com.wstester.model.Response;
import com.wstester.model.RestStep;
import com.wstester.model.UnitTestUtils;
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
    	RouteBuilder mockRoute = new RouteBuilder() {
			
			@Override
			public void configure() throws Exception {
				from("jms:topic:responseTopic")
				.to("mock:response");				
			}
		};
    	RestRoute restRoute = new RestRoute();
    	ExchangeDelayer delayer = new ExchangeDelayer();
    	
    	return new RouteBuilder[] {mongoRoute, restRoute, delayer, mockRoute};
    }
    
	@Test
	public void twoDependantTests() throws Exception{
		
		MockEndpoint resultEndpoint = getMockEndpoint("mock:response");
		resultEndpoint.expectedMessageCount(2);
		
		RestStep restStep = UnitTestUtils.getRestStep();
		MongoStep mongoStep = UnitTestUtils.getMongoStep();
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
		
		RestStep restStep = UnitTestUtils.getRestStep();
		MongoStep mongoStepDependant = UnitTestUtils.getMongoStep();
		mongoStepDependant.setDependsOn(restStep.getID());
		MongoStep mongoStep = UnitTestUtils.getMongoStep();
		
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
}
