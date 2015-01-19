package com.wstester.services.impl;

import java.util.Date;
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

import com.wstester.dispatcher.ResponseCallback;
import com.wstester.exceptions.WsException;
import com.wstester.log.CustomLogger;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IStepManager;
import com.wstester.services.definition.ITestRunner;
import com.wstester.services.definition.IVariableManager;
import com.wstester.util.ProjectProperties;

public class TestRunner implements ITestRunner {

	private TestProject testProject;
	private static Exception exceptionCaught;
	private static Boolean allStepsAdded;
	private CustomLogger log = new CustomLogger(TestRunner.class);

	public TestRunner() {
	}

	public TestRunner(TestProject testProject) {

		if (testProject != null) {
			log.info("Received " + testProject);
			this.testProject = testProject;
		}
	}

	@Override
	public void run(Object testToRun) {

		exceptionCaught = null;
		allStepsAdded = false;
		ExecutorService executor = Executors.newFixedThreadPool(10);
		executor.execute(new ProjectRunThread(testToRun));

		executor.shutdown();
	}

	@Override
	public Response getResponse(String stepId, Long timeout) {

		log.info(stepId, "Waiting response");

		// check if all the steps have been send to the camel queues
		log.info(stepId, "Waiting for all steps to be sent to the camel queues");
		waitForSteps(stepId);
		
		Date runDate = null;
		try {
			IStepManager stepManager = ServiceLocator.getInstance().lookup(IStepManager.class);
			ProjectProperties properties = new ProjectProperties();
			Long camelStepProcessingTimeout = properties.getLongProperty("camelStepProcessingTimeout");
			// the steps can have a delay until they are received by the StepManager
			while(runDate == null && camelStepProcessingTimeout >= 0) {
				runDate = stepManager.getLastRun(stepId);
				timeout -= 1000;
				Thread.sleep(1000);
			}
			
		} catch (Exception e) {
			exceptionCaught = e;
			e.printStackTrace();
		}
		
		if (runDate == null) {
			log.info(stepId, "The step did not previously run");
			return null;
		}
		
		Response response = ResponseCallback.getResponse(stepId, runDate);
		while (response == null && timeout >= 0) {
			if (exceptionCaught != null) {
				timeout = 0L;
				break;
			}
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			response = ResponseCallback.getResponse(stepId, runDate);
			timeout -= 1000;
		}

		if (timeout <= 0) {
			log.info(stepId, "The timeout expired!");
			if(exceptionCaught !=null && response == null) {
				log.info(stepId, "Exception was thrown in the TestRunner");
				
				// construct new Response to deliver the message in the UI
				response = new Response();
				response.setStepId(stepId);
				response.setStatus(ExecutionStatus.ERROR);
				response.setRunDate(new Date());
				response.setErrorMessage(exceptionCaught.getMessage());
			}
		}

		if (response != null) {
			log.info(stepId, response.toString());
		} else {
			log.info(stepId, "Response is null");
		}

		return response;
	}

	private void waitForSteps(String stepId) {

		ProjectProperties properties = new ProjectProperties();
		Long timeout = properties.getLongProperty("stepFinishTimeout");
		
		// wait until all steps are sent to the camel queues and are received by the StepManager
		while(!allStepsAdded && timeout > 0) {
			timeout -= 1000;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class ProjectRunThread implements Runnable {

		Object entityToRun;

		public ProjectRunThread(Object entityToRun) {

			log.info("Running " + entityToRun);
			this.entityToRun = entityToRun;

		}

		@Override
		public void run() {

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
				manageVariable();

				// Run the tests
				runSteps(session, entityToRun);

				allStepsAdded = true;
			} catch (Exception e) {
				exceptionCaught = e;
				e.printStackTrace();
			} finally {

				// Clean up
				try {
					if (session != null) {
						session.close();
					}
					if (connection != null) {
						connection.stop();
					}
				} catch (JMSException e) {
					exceptionCaught = e;
					e.printStackTrace();
				}
			}
		}
	}

	private void manageVariable() throws Exception {

		IVariableManager variableManager = ServiceLocator.getInstance().lookup(IVariableManager.class);
		
		// TODO: we don't have to add all variables, only the ones we use in the
		// current run
		if (testProject.getVariableList() != null) {
			for (Variable variable : testProject.getVariableList()) {
				
				variableManager.addVariable(variable);
				log.info("Added project variable: " + variable);
			}
		}

		for (TestSuite testSuite : testProject.getTestSuiteList()) {
			if (testSuite.getVariableList() != null) {
				for (Variable variable : testSuite.getVariableList()) {
					
					variableManager.addVariable(variable);
					log.info("Added suite variable: " + variable);
				}
			}

			for (TestCase testCase : testSuite.getTestCaseList()) {
				if (testCase.getVariableList() != null) {
					for (Variable variable : testCase.getVariableList()) {

						variableManager.addVariable(variable);
						log.info("Added case variable: " + variable);
					}
				}

				for (Step testStep : testCase.getStepList()) {
					if (testStep.getVariableList() != null) {
						for (Variable variable : testStep.getVariableList()) {

							variableManager.addVariable(variable);
							log.info("Added step variable: " + variable);
						}
					}
				}
			}
		}
		// notify the variableManager that all variables were sent
		variableManager.allVariablesSent();
	}

	private void runSteps(Session session, Object entityToRun) throws Exception {

		// When the constructor for StepManager is called a new instance of stepList is made
		ServiceLocator.getInstance().lookup(IStepManager.class);
		
		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue("startQueue");

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		if (entityToRun instanceof TestProject) {
			for (TestSuite testSuite : ((TestProject) entityToRun).getTestSuiteList()) {
				for (TestCase testCase : testSuite.getTestCaseList()) {
					for (Step testStep : testCase.getStepList()) {

						// Create a messages
						ObjectMessage message = session.createObjectMessage(testStep);

						// Tell the producer to send the message
						log.info(testStep.getID(), "Sent message to startQueue");
						producer.send(message);
					}
				}
			}

		} else if (entityToRun instanceof TestSuite) {
			for (TestCase testCase : ((TestSuite) entityToRun).getTestCaseList()) {
				for (Step testStep : testCase.getStepList()) {

					// Create a messages
					ObjectMessage message = session.createObjectMessage(testStep);

					// Tell the producer to send the message
					log.info(testStep.getID(), "Sent message to startQueue");
					producer.send(message);
				}
			}
		} else if (entityToRun instanceof TestCase) {
			for (Step testStep : ((TestCase) entityToRun).getStepList()) {

				// Create a messages
				ObjectMessage message = session.createObjectMessage(testStep);

				// Tell the producer to send the message
				log.info(testStep.getID(), "Sent message to startQueue");
				producer.send(message);
			}
		} else if (entityToRun instanceof Step) {

			Step testStep = (Step) entityToRun;
			// Create a messages
			ObjectMessage message = session.createObjectMessage(testStep);

			// Tell the producer to send the message
			log.info(testStep.getID(), "Sent message to startQueue");
			producer.send(message);
		} else {
			throw new WsException("Can't run object: " + entityToRun + "! Please run only instances of TestProject, TestSuite, TestCase or Step", null);
		}
	}
}