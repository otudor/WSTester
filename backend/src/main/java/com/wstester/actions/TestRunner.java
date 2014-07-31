package com.wstester.actions;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.soap.SOAPException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.wstester.client.Client;
import com.wstester.client.mongo.MongoDBClient;
import com.wstester.client.mysql.MySQLClient;
import com.wstester.client.rest.RestClient;
import com.wstester.client.soap.SOAPClient;
import com.wstester.dispatcher.AppConfig;
import com.wstester.dispatcher.Dispatcher;
import com.wstester.dispatcher.StepResult;
import com.wstester.exceptions.WsErrorCode;
import com.wstester.exceptions.WsException;
import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLService;
import com.wstester.model.Response;
import com.wstester.model.RestService;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapService;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;

public class TestRunner {

	private TestProject testProject;
	private ArrayList<Client> clientList = new ArrayList<Client>();
	
	public TestProject getTestProject() {
		return testProject;
	}

	public void setTestProject(TestProject testProject) {
		this.testProject = testProject;
	}
	
	public void run() throws Exception{
	
		Thread run = new Thread(new RunThread());
		run.start();
	}
	
	public Response getResponse(String stepId, Long timeout){
	
		System.out.println("Gettting response for: " + stepId);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		StepResult stepResult = (StepResult) context.getBean(StepResult.class);
		
		Response response = stepResult.getResponse(stepId);
		
		while (response == null || timeout > 0){
			try {
				Thread.sleep(1000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			response = stepResult.getResponse(stepId);
			timeout-=1000;
		} 
		
		context.close();
		
		return response;
	}
	
	private void instantiateClients() throws UnknownHostException, ClassNotFoundException, SQLException, UnsupportedOperationException, SOAPException {
		
		for (Environment env : testProject.getEnvironmentList()) {
			for (Server server : env.getServers()) {
				if (server.getServices() != null) {
					for (Service service : server.getServices()) {

						switch (service.getType()) {
							case MONGO: {
								MongoDBClient client = new MongoDBClient(server.getIp(), ((MongoService)service).getPort(), ((MongoService)service).getDbName(), ((MongoService)service).getUser(), ((MongoService)service).getPassword());
								client.setID(service.getID());
								clientList.add(client);
								break;
							}
							case MYSQL: {
								MySQLClient client = new MySQLClient(server.getIp(), ((MySQLService)service).getPort(), ((MySQLService)service).getDbName(), ((MySQLService)service).getUser(), ((MySQLService)service).getPassword());
								client.setID(service.getID());
								clientList.add(client);
								break;
							}
							case REST: {
								RestClient client = new RestClient(server.getIp() + ":" + ((RestService)service).getPort());
								client.setID(service.getID());
								clientList.add(client);
								break;
							}
							case SOAP:{
								SOAPClient client = new SOAPClient(((SoapService)service).getEndpoint());
								client.setID(service.getID());
								clientList.add(client);
							}
						}
					}
				}
			}
		}
	}

	private void destroyClients() throws Exception {

		for(Client client : clientList){
			client.close();
		}
	}
	
	private Client getTestClient(Step testStep) throws WsException {
		
		for(Client client : clientList){
			if(testStep.getService().getID().equals(client.getID())){
				return client;
			}
		}
		
		WsErrorCode errorCode = null;
		throw new WsException("No client was found for step: " + testStep.getName(), errorCode);
	}
	
	class RunThread implements Runnable{

		@Override
		public void run() {
			AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
			Dispatcher dispatcher = (Dispatcher) context.getBean(Dispatcher.class);

			try {
				instantiateClients();

				for (TestSuite testSuite : testProject.getTestSuiteList()) {
					for (TestCase testCase : testSuite.getTestCaseList()) {
						for (Step testStep : testCase.getStepList()) {
							dispatcher.dispatch(testStep,getTestClient(testStep));
						}
					}
				}

				context.close();
				destroyClients();
			} catch (Exception e) {

			}
		}
	}
}

