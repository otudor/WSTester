package com.wstester.camel.unittest;

import static org.apache.camel.component.jms.JmsComponent.jmsComponentClientAcknowledge;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

import com.wstester.dispatcher.RouteDispatcher;
import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class MockSystemTest extends CamelTestSupport{

	@Override
    protected CamelContext createCamelContext() throws Exception {
    	
        CamelContext camelContext = super.createCamelContext();
        
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistent=false");
        camelContext.addComponent("jms", jmsComponentClientAcknowledge(connectionFactory));
        
        return camelContext;
    }
    
    @Override
    protected RouteBuilder[] createRouteBuilders() throws Exception {
    	
    	RouteDispatcher routeDispatcher = new RouteDispatcher();
    	routeDispatcher.from("jms:restQueue").to("mock:rest");
    	routeDispatcher.from("jms:mockQueue").to("mock:mocking");
    	
    	return new RouteBuilder[] {routeDispatcher};
    }
    
    @Test
    public void messageSentOnlyToMockingQueue() throws Exception{
    	
    	MockEndpoint restEndpoint = getMockEndpoint("mock:rest");
    	MockEndpoint mockingEndpoint = getMockEndpoint("mock:mocking");
    	
    	TestProject testProject = TestUtils.getMockedRestProject();
    	setTestProject(testProject);
		
		template.sendBody("jms:startQueue", testProject.getTestSuiteList().get(0).getTestCaseList().get(0).getStepList().get(0));
		
		restEndpoint.expectedMessageCount(0);
		restEndpoint.assertIsSatisfied();
		mockingEndpoint.expectedMessageCount(1);
		mockingEndpoint.assertIsSatisfied();
    }
    
	protected void setTestProject(TestProject testProject) throws Exception {
		
		IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		projectFinder.setProject(testProject);
	}
}
