package com.wstester.actions;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.wstester.dispatcher.ResponseCallback;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.model.Variable;

public class TestRunner {
	
	private TestProject testProject;
	private AbstractXmlApplicationContext camelContext;
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	public TestProject getTestProject() {
		return testProject;
	}

	public void setTestProject(TestProject testProject) {
		this.testProject = testProject;
	}
	
	public void run(TestProject testProject) throws Exception{
	
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new ProjectRunThread(testProject));
		
		executor.shutdown();
	}
	
	public void run(TestSuite testSuite) throws Exception{
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new ProjectRunThread(testSuite));
		
		executor.shutdown();
	}
	
	public Response getResponse(String stepId, Long timeout){
	
		System.out.println("Gettting response for: " + stepId);
		
		Response response = ResponseCallback.getResponse(stepId);
		
		while (response == null && timeout > 0){
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			response = ResponseCallback.getResponse(stepId);
			timeout-=1000;
		} 
		
		return response;
	}
	
	class ProjectRunThread implements Runnable{

		Object entityToRun;
		
		public ProjectRunThread(Object entityToRun) {
			
			this.entityToRun = entityToRun;
		}

		@Override
		public void run() {

			camelContext = new ClassPathXmlApplicationContext("camel/CamelContext.xml");
			camelContext.start();
			
			try {
				// Create a ConnectionFactory
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

				// Create a Connection
				Connection connection = connectionFactory.createConnection();
				connection.start();

				// Create a Session
				Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

				manageVariable(session);
				runSteps(session);
				
				// Clean up
				session.close();
				connection.close();
				camelContext.close();
			} catch (Exception e){
				//TODO: auto generated block
			}
		}
	}
	private int runSteps (Session session  ) throws JMSException, InterruptedException{
		
		
		
		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue("startQueue");

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		int stepSize = 0;
			for (TestSuite testSuite : testProject.getTestSuiteList()) {
				for (TestCase testCase : testSuite.getTestCaseList()) {
					for (Step testStep : testCase.getStepList()) {
				
						stepSize ++;
						 // Create a messages
				         ObjectMessage message = session.createObjectMessage(testStep);
				        
				         // Tell the producer to send the message
				         System.out.println("Sent message: "+  testStep.getID());	
				         producer.send(message);
					}
				}
			}

			Long timeout = 10000L;
			while(!ResponseCallback.allResponsesReceived(stepSize) && (timeout-=1000) > 0){
				Thread.sleep(1000);
			
			}		
		return stepSize;

	}
	private int manageVariable (Session session  ) throws JMSException, InterruptedException {
		
		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue("variableQueue");

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		int variableSize = 0;
	
		for (Variable variable : testProject.getVariableList()){
			
			ObjectMessage messageProject = session.createObjectMessage(variable);
			System.out.println("for each" +variable);
			producer.send(messageProject);
			variableSize++;
		
		}
			for (TestSuite testSuite : testProject.getTestSuiteList()) {
				 for (Variable variable : testSuite.getVariableList()){
				
				ObjectMessage messageSuite = session.createObjectMessage(variable);
				producer.send(messageSuite);
				variableSize++;
				 }
				for (TestCase testCase : testSuite.getTestCaseList()) {
					 for (Variable variable : testCase.getVariableList()){
					 
						 ObjectMessage messageCase = session.createObjectMessage(variable);
						 producer.send(messageCase);
						 variableSize++;
					 }
					 
					 for (Step testStep : testCase.getStepList()) {
						
					   for (Variable variable : testStep.getVariableList()){
						
						 // Create a messages
				         ObjectMessage message = session.createObjectMessage(variable);
				         producer.send(message);
				         variableSize ++;
					   }
					}
				}
			}
			
			Long timeout = 10000L;
			while(!ResponseCallback.allResponsesReceived(variableSize) && (timeout-=1000) > 0){
				Thread.sleep(1000);
			
			}		
			
		return variableSize;

	}
}
