package com.wstester.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestUtils {

	public static TestProject getTestPlan(){
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");
		
		// construct asset list
		List<Asset> assetList = new ArrayList<Asset>();
		Asset asset1 = new Asset();
		Asset asset2 = new Asset();
		Asset asset3 = new Asset();
		Asset asset4 = new Asset();
		assetList.add(asset1);
		assetList.add(asset2);
		assetList.add(asset3);
		assetList.add(asset4);
		testProject.setAssetList(assetList);

		// construct service list
		// Service 1
		List<Service> serviceList1 = new ArrayList<Service>();
		RestService restService = new RestService();
		restService.setName("Service Rest");
		restService.setPort("9997");
		serviceList1.add(restService);
		
		// Service 2
		List<Service> serviceList2 = new ArrayList<Service>();
		MongoService service2 = new MongoService();
		service2.setName("Service Mongo");
		service2.setPort("27017");
		service2.setDbName("test");
		service2.setUser("appuser");
		service2.setPassword("apppass");
		serviceList2.add(service2);
		
		// Service 3
		List<Service> serviceList3 = new ArrayList<Service>();
		SoapService service3 = new SoapService();
		service3.setName("Service SOAP");
		service3.setEndpoint("http://footballpool.dataaccess.eu:80/data/info.wso");
		serviceList3.add(service3);
		
		// Service 4
		List<Service> serviceList4 = new ArrayList<Service>();
		MySQLService service4 = new MySQLService();
		service4.setName("Service MYSQL");
		service4.setPort("3306");
		service4.setDbName("test");
		service4.setUser("appuser");
		service4.setPassword("apppass");
		serviceList4.add(service4);
		
		// construct server list
		List<Server> serverList1 = new ArrayList<Server>();
		// Server 1
		Server server11 = new Server();
		server11.setDescription("This is the first server of the first env");
		server11.setIp("localhost");
		server11.setName("Server 11");
		server11.setServices(serviceList1);
		serverList1.add(server11);
		// Server 2
		Server server12 = new Server();
		server12.setDescription("This is the second server of the first env");
		server12.setIp("localhost");
		server12.setName("Server 12");
		server12.setServices(serviceList2);
		serverList1.add(server12);

		// Server 3
		List<Server> serverList2 = new ArrayList<Server>();
		Server server21 = new Server();
		server21.setDescription("This is the first server of the second env");
		server21.setIp("localhost");
		server21.setName("Server 21");
		server21.setServices(serviceList4);
		serverList2.add(server21);
		// Server 4
		Server server22 = new Server();
		server22.setDescription("This is the second server of the second env");
		server22.setIp("localhost");
		server22.setName("Server 22");
		server22.setServices(serviceList3);
		serverList2.add(server22);
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
		// Environment 1
		Environment env1 = new Environment();
		env1.setName("Env 1");
		env1.setServers(serverList1);
		// Environment 2
		Environment env2 = new Environment();
		env2.setName("Env 2");
		env2.setServers(serverList2);
		environmentList.add(env1);
		environmentList.add(env2);
		testProject.setEnvironmentList(environmentList);

		// construct test steps
		// test 1
		List<Step> stepList1 = new ArrayList<Step>();
		RestStep step1 = new RestStep();
		step1.setName("Step 1");
		step1.setServer(server11);
		step1.setService(restService);
		List<Asset> assetList1 = new ArrayList<Asset>();
		assetList1.add(asset1);
		step1.setAssetList(assetList1);
		List<Assert> assertList = new ArrayList<Assert>();
		Assert oneAssert = new Assert();
		oneAssert.setAsserts("First assert");
		assertList.add(oneAssert);
		step1.setAssertList(assertList);
		stepList1.add(step1);
		// test 2
		MongoStep step2 = new MongoStep();
		step2.setName("Step 2");
		step2.setServer(server12);
		step2.setService(service2);
		List<Asset> assetList2 = new ArrayList<Asset>();
		assetList2.add(asset2);
		step2.setAssetList(assetList2);
		List<Assert> assertList2 = new ArrayList<Assert>();
		Assert oneAssert2 = new Assert();
		oneAssert2.setAsserts("Second assert");
		assertList2.add(oneAssert2);
		step2.setAssertList(assertList2);
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		query.put("name", "HAC");
		step2.setAction(Action.SELECT);
		step2.setCollection(collection);
		step2.setQuery(query);
		stepList1.add(step2);
		
		// test 3
		List<Step> stepList2 = new ArrayList<Step>();
		MySQLStep step3 = new MySQLStep();
		step3.setName("Step 3");
		step3.setServer(server21);
		step3.setService(service4);
		List<Asset> assetList3 = new ArrayList<Asset>();
		assetList3.add(asset3);
		step3.setAssetList(assetList3);
		List<Assert> assertList3 = new ArrayList<Assert>();
		Assert oneAssert3 = new Assert();
		oneAssert3.setAsserts("Third assert");
		assertList3.add(oneAssert3);
		step3.setAssertList(assertList3);
		stepList2.add(step3);
		// test 4
		SoapStep step4 = new SoapStep();
		step4.setName("Step 4");
		step4.setServer(server22);
		step4.setService(service3);
		List<Asset> assetList4 = new ArrayList<Asset>();
		assetList4.add(asset4);
		step4.setAssetList(assetList4);
		List<Assert> assertList4 = new ArrayList<Assert>();
		Assert oneAssert4 = new Assert();
		oneAssert4.setAsserts("Forth assert");
		assertList4.add(oneAssert4);
		step4.setAssertList(assertList4);
		stepList2.add(step4);
		
		// construct test case list
		// test case 1
		List<TestCase> testCaseList1 = new ArrayList<TestCase>();
		TestCase testCase = new TestCase();
		testCase.setName("TC 1");
		testCase.setStepList(stepList1);
		testCaseList1.add(testCase);
		
		// test case 2		
		List<TestCase> testCaseList2 = new ArrayList<TestCase>();
		TestCase testCase2 = new TestCase();
		testCase2.setName("TC 1");
		testCase2.setStepList(stepList2);
		testCaseList2.add(testCase2);
		
		// construct test suite list
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
		TestSuite testSuite1 = new TestSuite();
		testSuite1.setName("Test Suite 1");
		testSuite1.setEnvironment(env1);
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
		TestSuite testSuite2 = new TestSuite();
		testSuite2.setName("Test Suite 2");
		testSuite2.setEnvironment(env2);
		testSuite2.setTestCaseList(testCaseList2);
		testSuiteList.add(testSuite2);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}
	
	public static TestProject getRestTestPlan(){
		
		TestProject testPlan = new TestProject();
		testPlan.setName("Rest Test Plan");
		
		// construct asset list
		List<Asset> assetList = new ArrayList<Asset>();
		Asset asset1 = new Asset();
		Asset asset2 = new Asset();
		Asset asset3 = new Asset();
		Asset asset4 = new Asset();
		assetList.add(asset1);
		assetList.add(asset2);
		assetList.add(asset3);
		assetList.add(asset4);
		testPlan.setAssetList(assetList);

		// construct service list
		// Service 1
		List<Service> serviceList1 = new ArrayList<Service>();
		RestService restService = new RestService();
		restService.setName("Service Rest");
		restService.setPort("9997");
		serviceList1.add(restService);
		
		// construct server list
		List<Server> serverList1 = new ArrayList<Server>();
		// Server 1
		Server server11 = new Server();
		server11.setDescription("This is the first server of the first env");
		server11.setIp("localhost");
		server11.setName("Server 11");
		server11.setServices(serviceList1);
		serverList1.add(server11);
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
		// Environment 1
		Environment env1 = new Environment();
		env1.setName("Env 1");
		env1.setServers(serverList1);
		environmentList.add(env1);
		testPlan.setEnvironmentList(environmentList);

		// construct test steps
		// test 1
		List<Step> stepList1 = new ArrayList<Step>();
		RestStep step1 = new RestStep();
		step1.setName("Step 1");
		step1.setServer(server11);
		step1.setService(restService);
		List<Asset> assetList1 = new ArrayList<Asset>();
		assetList1.add(asset1);
		step1.setAssetList(assetList1);
		List<Assert> assertList = new ArrayList<Assert>();
		Assert oneAssert = new Assert();
		oneAssert.setAsserts("First assert");
		assertList.add(oneAssert);
		step1.setAssertList(assertList);
		stepList1.add(step1);
		
		// construct test case list
		// test case 1
		List<TestCase> testCaseList1 = new ArrayList<TestCase>();
		TestCase testCase = new TestCase();
		testCase.setName("TC 1");
		testCase.setStepList(stepList1);
		testCaseList1.add(testCase);
		
		// construct test suite list
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
		TestSuite testSuite1 = new TestSuite();
		testSuite1.setName("Test Suite 1");
		testSuite1.setEnvironment(env1);
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
	
		testPlan.setTestSuiteList(testSuiteList);
		
		return testPlan;
	}
	
	public static TestProject getMongoTestPlan(){
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");
		
		// construct asset list
		List<Asset> assetList = new ArrayList<Asset>();
		Asset asset1 = new Asset();
		Asset asset2 = new Asset();
		Asset asset3 = new Asset();
		Asset asset4 = new Asset();
		assetList.add(asset1);
		assetList.add(asset2);
		assetList.add(asset3);
		assetList.add(asset4);
		testProject.setAssetList(assetList);

		// construct service list		
		// Service 2
		List<Service> serviceList2 = new ArrayList<Service>();
		MongoService service2 = new MongoService();
		service2.setName("Service Mongo");
		service2.setPort("27017");
		service2.setDbName("test");
		service2.setUser("appuser");
		service2.setPassword("apppass");
		serviceList2.add(service2);
		
		// construct server list
		List<Server> serverList1 = new ArrayList<Server>();
		// Server 2
		Server server12 = new Server();
		server12.setDescription("This is the second server of the first env");
		server12.setIp("localhost");
		server12.setName("Server 12");
		server12.setServices(serviceList2);
		serverList1.add(server12);
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
		// Environment 1
		Environment env1 = new Environment();
		env1.setName("Env 1");
		env1.setServers(serverList1);
		environmentList.add(env1);

		testProject.setEnvironmentList(environmentList);

		// construct test steps

		List<Step> stepList1 = new ArrayList<Step>();
		// test 2
		MongoStep step2 = new MongoStep();
		step2.setName("Step 2");
		step2.setServer(server12);
		step2.setService(service2);
		List<Asset> assetList2 = new ArrayList<Asset>();
		assetList2.add(asset2);
		step2.setAssetList(assetList2);
		List<Assert> assertList2 = new ArrayList<Assert>();
		Assert oneAssert2 = new Assert();
		oneAssert2.setAsserts("Second assert");
		assertList2.add(oneAssert2);
		step2.setAssertList(assertList2);
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		query.put("name", "HAC");
		step2.setAction(Action.SELECT);
		step2.setCollection(collection);
		step2.setQuery(query);
		stepList1.add(step2);
		
		// construct test case list
		// test case 1
		List<TestCase> testCaseList1 = new ArrayList<TestCase>();
		TestCase testCase = new TestCase();
		testCase.setName("TC 1");
		testCase.setStepList(stepList1);
		testCaseList1.add(testCase);
		
		// construct test suite list
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
		TestSuite testSuite1 = new TestSuite();
		testSuite1.setName("Test Suite 1");
		testSuite1.setEnvironment(env1);
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}
}
