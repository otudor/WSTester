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
import com.wstester.model.RestService;
import com.wstester.model.RestStep;
import com.wstester.model.Service;
import com.wstester.model.ServiceStatus;

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
    public void messageSentOnlyToMockingQueue() throws InterruptedException{
    	
    	MockEndpoint restEndpoint = getMockEndpoint("mock:rest");
    	MockEndpoint mockingEndpoint = getMockEndpoint("mock:mocking");
    	
    	RestStep step = new RestStep();
    	Service service = new RestService();
    	service.setStatus(ServiceStatus.MOCKED);
		step.setService(service);
		
		template.sendBody("jms:startQueue", step);
		
		restEndpoint.expectedMessageCount(0);
		restEndpoint.assertIsSatisfied();
		mockingEndpoint.expectedMessageCount(1);
		mockingEndpoint.assertIsSatisfied();
    }
}
