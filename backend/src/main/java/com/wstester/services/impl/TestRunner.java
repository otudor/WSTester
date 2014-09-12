package com.wstester.services.impl;

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
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wstester.customLogger.MyLogger;
import com.wstester.dispatcher.ResponseCallback;
import com.wstester.exceptions.WsException;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.model.Variable;
import com.wstester.services.definition.ITestRunner;
import com.wstester.variables.VariableManager;

public class TestRunner implements ITestRunner{
	
	private AbstractXmlApplicationContext camelContext;
	private TestProject testProject;
	protected MyLogger mylog = new MyLogger("Logger");
	
	public TestRunner(TestProject testProject){
		
		this.testProject = testProject;
	}
	
	@Override
	public void run(TestProject testProject) throws Exception{
	
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new ProjectRunThread(testProject));
		
		executor.shutdown();
	}
	
	@Override
	public void run(TestSuite testSuite) throws Exception{
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new ProjectRunThread(testSuite));
		
		executor.shutdown();
	}
	
	@Override
	public void run(TestCase testCase) throws Exception{
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new ProjectRunThread(testCase));
		
		executor.shutdown();
	}

	@Override
	public void run(Step testStep) throws Exception{
		
		ExecutorService executor = Executors.newFixedThreadPool(1);
		executor.execute(new ProjectRunThread(testStep));
		
		executor.shutdown();
	}
	
	@Override
	public Response getResponse(String stepId, Long timeout){
	
		mylog.info("Gettting response for: ",  stepId);
		
		Response response = ResponseCallback.getResponse(stepId);
		
		while(response == null && timeout > 0){
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
			
			Connection connection = null;
			Session session = null;
			try {
				// Create a ConnectionFactory
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

				// Create a Connection
				connection = connectionFactory.createConnection();
				connection.start();

				// Create a Session
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

				// Manage the variables
				manageVariable(session);
				
				// Run the tests
				runSteps(session, entityToRun);
			} catch (Exception e){
				//TODO: auto generated block
				e.printStackTrace();
			} finally{
				
				// Clean up
				try {
					connection.close();
					session.close();
					camelContext.close();
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void manageVariable(Session session) throws JMSException, InterruptedException{
		
		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue("variableQueue");

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		
		int variableSize = 0;
		
		//TODO: we don't have to add all variables, only the ones we use in the current run
		if(testProject.getVariableList() != null){
			for(Variable variable : testProject.getVariableList()){
		
				ObjectMessage messageProject = session.createObjectMessage(variable);
				producer.send(messageProject);
				variableSize++;
			}
		}
		
		for(TestSuite testSuite : testProject.getTestSuiteList()){
			if(testSuite.getVariableList() != null){
				for(Variable variable : testSuite.getVariableList()){

					ObjectMessage messageSuite = session.createObjectMessage(variable);
					producer.send(messageSuite);
					variableSize++;
				}
			}
			
			for(TestCase testCase : testSuite.getTestCaseList()){
				if(testCase.getVariableList() != null){
					for(Variable variable : testCase.getVariableList()){

						ObjectMessage messageCase = session.createObjectMessage(variable);
						producer.send(messageCase);
						variableSize++;
					}
				}

				for(Step testStep : testCase.getStepList()){
					if(testStep.getVariableList() != null){
						for(Variable variable : testStep.getVariableList()){

							ObjectMessage message = session.createObjectMessage(variable);
							producer.send(message);
							variableSize++;
						}
					}
				}
			}
		}

		// TODO: change timeout to configuration file
		Long timeout = 10000L;
		while(!VariableManager.allVariablesReceived(variableSize) && ((timeout -= 1000) > 0)){
			
			Thread.sleep(1000);
		}	
	}

	private void runSteps(Session session, Object entityToRun) throws JMSException, InterruptedException, WsException{

		
		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue("startQueue");

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		int stepSize = 0;
		
		if(entityToRun instanceof TestProject){
			for(TestSuite testSuite : ((TestProject) entityToRun).getTestSuiteList()){
				for(TestCase testCase : testSuite.getTestCaseList()){
					for(Step testStep : testCase.getStepList()){

						// Create a messages
						ObjectMessage message = session.createObjectMessage(testStep);

						// Tell the producer to send the message
						mylog.info("Sent message: " , testStep.getID());
						producer.send(message);
						stepSize++;
					}
				}
			}

		}
		else if(entityToRun instanceof TestSuite){
			for(TestCase testCase : ((TestSuite) entityToRun).getTestCaseList()){
				for(Step testStep : testCase.getStepList()){

					// Create a messages
					ObjectMessage message = session.createObjectMessage(testStep);

					// Tell the producer to send the message
					mylog.info("Sent message: " + testStep.getID());
					producer.send(message);
					stepSize++;
				}
			}
		}
		else if(entityToRun instanceof TestCase){
			for(Step testStep : ((TestCase)entityToRun).getStepList()){
				
				// Create a messages
				ObjectMessage message = session.createObjectMessage(testStep);

				// Tell the producer to send the message
				mylog.info("Sent message: " + testStep.getID());
				producer.send(message);
				stepSize++;
			}
		}
		else if(entityToRun instanceof Step){

			// Create a messages
			ObjectMessage message = session.createObjectMessage((Step)entityToRun);

			// Tell the producer to send the message
			mylog.info("Sent message: " + ((Step)entityToRun).getID());
			producer.send(message);
			stepSize++;
		}
		else{
			throw new WsException("Can't run object: " + entityToRun + "! Please run only instances of TestProject, TestSuite, TestCase or Step", null);
		}
		
		// TODO: change timeout to configuration file
		Long timeout = 10000L;
		while (!ResponseCallback.allResponsesReceived(stepSize) && ((timeout -= 1000) > 0)) {
			Thread.sleep(1000);
		}
	}
}
