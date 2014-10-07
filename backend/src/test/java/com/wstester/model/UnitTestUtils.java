package com.wstester.model;

import java.util.HashMap;

public class UnitTestUtils {

	private static Server getServer(){
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
	
	public static MongoStep getMongoStep(){
		MongoStep mongoStep = new MongoStep();
		mongoStep.setName("Step 2");
		mongoStep.setServer(getServer());
		mongoStep.setService(getMongoService());
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		query.put("name", "HAC");
		mongoStep.setAction(Action.SELECT);
		mongoStep.setCollection(collection);
		mongoStep.setQuery(query);
		return mongoStep;
	}
	
	public static RestStep getRestStep(){
		RestStep step = new RestStep();
		step.setName("Step 1");
		step.setServer(getServer());
		step.setService(getRestService());
		step.setPath("/customer/getCustomers");
		step.setMethod(RestMethod.GET);
		return step;
	}
}
