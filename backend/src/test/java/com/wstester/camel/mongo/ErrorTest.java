package com.wstester.camel.mongo;

import static org.apache.camel.component.jms.JmsComponent.jmsComponentClientAcknowledge;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.impl.JndiRegistry;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.mongodb.Mongo;
import com.wstester.dispatcher.mongo.MongoRoute;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class ErrorTest extends CamelTestSupport{
	
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
    	return new RouteBuilder[] {mongoRoute, mockRoute};
    }
    
	@Test
	public void connectionError() throws Exception{
		String exceptionMessage = "com.mongodb.MongoException$Network: Read operation to server /127.0.0.1:27017 failed on database test";
		
		RouteDefinition route = context.getRouteDefinitions().get(0);
		route.adviceWith(context, new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("mongodb://*")
					.skipSendToOriginalEndpoint()
					.throwException(new ConnectException(exceptionMessage));
			}
		});
		
		MockEndpoint resultEndpoint = getMockEndpoint("mock:response");
		resultEndpoint.expectedMessageCount(1);
		
		TestProject testProject = TestUtils.getMongoTestPlan();
		setTestProject(testProject);
		
		template.sendBody("jms:mongoQueue", testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0));
		
		resultEndpoint.await(5, TimeUnit.SECONDS);
		resultEndpoint.assertIsSatisfied();
		Response response = resultEndpoint.getReceivedExchanges().get(0).getIn().getBody(Response.class);
		assertEquals(ExecutionStatus.ERROR, response.getStatus());		
		assertEquals("ConnectException:" + exceptionMessage, response.getErrorMessage());
	}
	
	@Test
	public void unknownHostError() throws Exception{
		String exceptionMessage = "java.net.UnknownHostException: Host is unkown";
		
		RouteDefinition route = context.getRouteDefinitions().get(0);
		route.adviceWith(context, new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				interceptSendToEndpoint("mongodb://*")
					.skipSendToOriginalEndpoint()
					.throwException(new UnknownHostException(exceptionMessage));
			}
		});
		
		MockEndpoint resultEndpoint = getMockEndpoint("mock:response");
		resultEndpoint.expectedMessageCount(1);

		TestProject testProject = TestUtils.getMongoTestPlan();
		setTestProject(testProject);
		
		template.sendBody("jms:mongoQueue", testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0));
		
		resultEndpoint.await(5, TimeUnit.SECONDS);
		resultEndpoint.assertIsSatisfied();
		Response response = resultEndpoint.getReceivedExchanges().get(0).getIn().getBody(Response.class);
		assertEquals(ExecutionStatus.ERROR, response.getStatus());
		assertEquals("UnknownHostException:" + exceptionMessage, response.getErrorMessage());
	}
	
	protected void setTestProject(TestProject testProject) throws Exception {
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		projectFinder.setProject(testProject);
	}
}
