package com.wstester.services.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.wstester.dispatcher.ResponseCallback;
import com.wstester.exceptions.WsException;
import com.wstester.log.CustomLogger;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.model.Variable;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IStepManager;
import com.wstester.services.definition.ITestRunner;
import com.wstester.util.ProjectProperties;
import com.wstester.variable.VariableRoute;

public class TestRunner implements ITestRunner {

	private TestProject testProject;
	private static Date runDate;
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
	public void run(Object testToRun) throws Exception {

		runDate = new Date();
		ExecutorService executor = Executors.newFixedThreadPool(10);
		executor.execute(new ProjectRunThread(testToRun));

		executor.shutdown();
	}

	@Override
	public Response getResponse(String stepId, Long timeout) {

		log.info(stepId, "Waiting response");

		Response response = ResponseCallback.getResponse(stepId, runDate);

		while (response == null && timeout > 0) {
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			response = ResponseCallback.getResponse(stepId, runDate);
			timeout -= 1000;
			System.out.println(timeout);
		}

		if (timeout < 0) {
			log.info(stepId, "The timeout expired!");
		}

		if (response != null) {
			log.info(stepId, response.toString());
		} else {
			log.info(stepId, "Response is null");
		}

		return response;
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
				manageVariable(session);

				// Run the tests
				runSteps(session, entityToRun);

				// send a message to a finishTopic meaning that the current run has finished
				sendFinishMessage(session, entityToRun);
			} catch (RuntimeException e) {
				// TODO: auto generated block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO: auto generated block
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void manageVariable(Session session) throws JMSException, InterruptedException {

		// Create the destination (Topic or Queue)
		Destination destination = session.createQueue("variableQueue");

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		int variableSize = 0;

		// TODO: we don't have to add all variables, only the ones we use in the
		// current run
		if (testProject.getVariableList() != null) {
			for (Variable variable : testProject.getVariableList()) {

				ObjectMessage messageProject = session.createObjectMessage(variable);
				producer.send(messageProject);
				variableSize++;
				log.info("Added project variable: " + variable);
			}
		}

		for (TestSuite testSuite : testProject.getTestSuiteList()) {
			if (testSuite.getVariableList() != null) {
				for (Variable variable : testSuite.getVariableList()) {

					ObjectMessage messageSuite = session.createObjectMessage(variable);
					producer.send(messageSuite);
					variableSize++;
					log.info("Added suite variable: " + variable);
				}
			}

			for (TestCase testCase : testSuite.getTestCaseList()) {
				if (testCase.getVariableList() != null) {
					for (Variable variable : testCase.getVariableList()) {

						ObjectMessage messageCase = session.createObjectMessage(variable);
						producer.send(messageCase);
						variableSize++;
						log.info("Added case variable: " + variable);
					}
				}

				for (Step testStep : testCase.getStepList()) {
					if (testStep.getVariableList() != null) {
						for (Variable variable : testStep.getVariableList()) {

							ObjectMessage message = session.createObjectMessage(variable);
							producer.send(message);
							variableSize++;
							log.info("Added step variable: " + variable);
						}
					}
				}
			}
		}
		ProjectProperties properties = new ProjectProperties();
		Long timeout = properties.getLongProperty("stepFinishTimeout");
		// TODO: change the source of the allVariablesReceived from
		// VariableRoute to VariableManager
		while (!VariableRoute.allVariablesReceived(variableSize) && ((timeout -= 1000) > 0)) {
			Thread.sleep(1000);
		}
	}

	private void runSteps(Session session, Object entityToRun) throws Exception {

		// When the constructor for StepManager is called a new instance of stepList is made
		ServiceLocator.getInstance().lookup(IStepManager.class, true);
		
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

	private void sendFinishMessage(Session session, Object entityToRun) throws JMSException {

		// Create the destination (Topic or Queue)
		Destination destination = session.createTopic("finishTopic");

		// Create a MessageProducer from the Session to the Topic or Queue
		MessageProducer producer = session.createProducer(destination);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		Message message = session.createObjectMessage((Serializable) entityToRun);
		producer.send(message);
	}
}