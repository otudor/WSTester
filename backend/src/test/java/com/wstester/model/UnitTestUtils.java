package com.wstester.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UnitTestUtils {

	public static Server getServer(){
		return new Server("Server", "localhost", "description");
	}
	
	private static RestService getRestService() {
		RestService service = new RestService();
		service.setName("restService");
		service.setPort("9997");
		return service;
	}
	
	private static MongoService getMongoService(){
		MongoService service = new MongoService();
		service.setName("Mongo Service");
		service.setPort("27017");
		service.setDbName("test");
		service.setUser("appuser");
		service.setPassword("apppass");
		return service;
	}
	
	public static MongoStep getMongoStep() {
		
		Server server = getServer();
		
		MongoStep mongoStep = new MongoStep();
		mongoStep.setName("Step 2");
		mongoStep.setServerId(server.getId());
		mongoStep.setService(getMongoService());
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		query.put("name", "HAC");
		mongoStep.setAction(Action.SELECT);
		mongoStep.setCollection(collection);
		mongoStep.setQuery(query);
		return mongoStep;
	}
	
	public static RestStep getRestStep() {
		
		Server server = getServer();
		
		RestStep step = new RestStep();
		step.setName("Step 1");
		step.setServerId(server.getId());
		step.setService(getRestService());
		step.setPath("/customer/getCustomers");
		step.setMethod(RestMethod.GET);
		return step;
	}
	
	public static TestProject getTestProject() {
		
		TestProject testProject = new TestProject();
		
		List<Environment> environmentList = new ArrayList<Environment>();
		Environment environment = new Environment();
//		environment.setServers(servers);
		environmentList.add(environment);
		testProject.setEnvironmentList(environmentList);
		return null;
	}
}
