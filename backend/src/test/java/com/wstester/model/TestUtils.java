package com.wstester.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wstester.model.RestRule.InputType;

public class TestUtils {

	public static TestProject getTestPlan() throws IOException{
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");
		
		// construct asset list
		List<Asset> assetList = new ArrayList<Asset>();
		Asset asset1 = new Asset();
		asset1.setLastmodified(100122155);
		asset1.setName("asset1");
		asset1.setPath("C:/a/b/c");
		asset1.setSize(200);
		asset1.setType("PDF");
		Asset asset2 = new Asset();
		asset2.setLastmodified(1100223344);
		asset2.setName("asset2");
		asset2.setPath("D:/path/to/file");
		asset2.setSize(500);
		asset2.setType("TXT");
		assetList.add(asset1);
		assetList.add(asset2);
		testProject.setAssetList(assetList);

		// construct the variable list
		List<Variable> variableList = new ArrayList<Variable>();
		Variable globalVariable = new Variable("content", "name");
		Variable localVariable = new Variable("name", VariableType.INTEGER, "2");
		variableList.add(globalVariable);
		variableList.add(localVariable);
		testProject.setVariableList(variableList);
		
		// construct service list
		// Service 1
		List<Service> serviceList1 = new ArrayList<Service>();
		RestService restService = new RestService();
		restService.setName("Service Rest");
		restService.setPort("9997");
		List<Rule> restRuleList = new ArrayList<Rule>();
		Rule restRule = new RestRule(InputType.METHOD, "input", "output");
		restRuleList.add(restRule);
		restService.setRuleList(restRuleList);
		restService.setStatus(ServiceStatus.AVAILABLE);
		serviceList1.add(restService);
		
		// Service 2
		List<Service> serviceList2 = new ArrayList<Service>();
		MongoService mongoService = new MongoService();
		mongoService.setName("Service Mongo");
		mongoService.setPort("27017");
		mongoService.setDbName("test");
		mongoService.setUser("appuser");
		mongoService.setPassword("apppass");
		List<Rule> mongoRuleList = new ArrayList<Rule>();
		Rule mongoRule = new MongoRule(com.wstester.model.MongoRule.InputType.COLLECTION, "input", "output");
		mongoRuleList.add(mongoRule );
		mongoService.setRuleList(mongoRuleList);
		mongoService.setStatus(ServiceStatus.AVAILABLE);
		serviceList2.add(mongoService);
		
		// Service 3
		List<Service> serviceList3 = new ArrayList<Service>();
		SoapService soapService = new SoapService();
		soapService.setName("Service SOAP");
		soapService.setPort("80");
		soapService.setPath("/data/info.wso");
		soapService.setWsdlURL("http://footballpool.dataaccess.eu/data/info.wso?wsdl");
		List<Rule> soapRuleList = new ArrayList<Rule>();
		Rule soapRule = new SoapRule(com.wstester.model.SoapRule.InputType.REQUEST, "input", "output");
		soapRuleList.add(soapRule);
		soapService.setRuleList(soapRuleList);
		soapService.setStatus(ServiceStatus.AVAILABLE);
		List<Authentication> authenticationList = new ArrayList<Authentication>();
		Authentication authentication = new Authentication();
		authentication.setPassword("pass");
		authentication.setRole("role");
		authentication.setUsername("username");
		authenticationList.add(authentication );
		soapService.setAuthenticationList(authenticationList );
		serviceList3.add(soapService);
		
		// Service 4
		List<Service> serviceList4 = new ArrayList<Service>();
		MySQLService mysqlService = new MySQLService();
		mysqlService.setName("Service MYSQL");
		mysqlService.setPort("3306");
		mysqlService.setDbName("test");
		mysqlService.setUser("appuser");
		mysqlService.setPassword("apppass");
		List<Rule> mysqlRuleList = new ArrayList<Rule>();
		Rule mysqlRule = new MysqlRule(com.wstester.model.MysqlRule.InputType.OPERATION, "input", "output");
		mysqlRuleList.add(mysqlRule );
		mysqlService.setRuleList(mysqlRuleList );
		mysqlService.setStatus(ServiceStatus.AVAILABLE);
		serviceList4.add(mysqlService);
		
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
		server22.setIp("http://footballpool.dataaccess.eu");
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
		RestStep restStep = new RestStep();
		restStep.setName("Step 1");
		restStep.setServerId(server11.getId());
		restStep.setServiceId(restService.getId());
		List<Assert> assertList = new ArrayList<Assert>();
		Assert oneAssert = new Assert();
		oneAssert.setExpected("All customers");
		assertList.add(oneAssert);
		restStep.setAssertList(assertList);
		restStep.setPath("/customer/getCustomers");
		restStep.setMethod(RestMethod.GET);
		stepList1.add(restStep);
		// test 2
		MongoStep step2 = new MongoStep();
		step2.setName("Step 2");
		step2.setServerId(server12.getId());
		step2.setServiceId(mongoService.getId());
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		query.put("name", "HAC");
		step2.setAction(Action.SELECT);
		step2.setCollection(collection);
		step2.setQuery(query);
		step2.setDependsOn(restStep.getId());
		stepList1.add(step2);
		
		// test 3
		List<Step> stepList2 = new ArrayList<Step>();
		MySQLStep step3 = new MySQLStep();
		step3.setName("Step 3");
		step3.setServerId(server21.getId());
		step3.setServiceId(mysqlService.getId());
		step3.setOperation("SELECT * FROM angajati");
		stepList2.add(step3);
		// test 4
		SoapStep step4 = new SoapStep();
		step4.setName("Step 4");
		step4.setServerId(server22.getId());
		step4.setServiceId(soapService.getId());
		String request = new String(Files.readAllBytes(Paths.get("src/test/resources/SOAPRequest.xml")));
		step4.setRequest(request);
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
		testSuite1.setEnvironmentId(env1.getId());
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
		TestSuite testSuite2 = new TestSuite();
		testSuite2.setName("Test Suite 2");
		testSuite2.setEnvironmentId(env2.getId());
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

		// construct the variable list
		List<Variable> variableList = new ArrayList<Variable>();
		Variable variable2 = new Variable();
		variable2.setName("name");
		variable2.setType(VariableType.STRING);
		variable2.setSelector("response:");
		variableList.add(variable2);
		testPlan.setVariableList(variableList);
		
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
		RestStep restStep = new RestStep();
		restStep.setName("Step 1");
		restStep.setServerId(server11.getId());
		restStep.setServiceId(restService.getId());
		restStep.setPath("/customer/getCustomers");
		restStep.setMethod(RestMethod.GET);
		restStep.addVariable(variable2.getId());
		stepList1.add(restStep);
		
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
		testSuite1.setEnvironmentId(env1.getId());
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
		step2.setServerId(server12.getId());
		step2.setServiceId(service2.getId());
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
		testSuite1.setEnvironmentId(env1.getId());
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}
	
	public static TestProject getMongoTestPlanInsert(){
		
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

		ArrayList<Step> stepList1 = new ArrayList<Step>();
		// select before
		MongoStep step1 = new MongoStep();
		step1.setName("Step 1");
		step1.setServerId(server12.getId());
		step1.setServiceId(service2.getId());
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		String name = "Ana";
		String keyName = "name";
		query.put(keyName, name);
		step1.setAction(Action.SELECT);
		step1.setCollection(collection);
		step1.setQuery(query);
		stepList1.add(step1);

		// insert
		MongoStep step2 = new MongoStep();
		step2.setName("Step 2");
		step2.setServerId(server12.getId());
		step2.setServiceId(service2.getId());
		HashMap<String, String> insertQuery = new HashMap<String, String>();
		String id = "100";
		String keyId = "id";
		insertQuery.put(keyName, name);
		insertQuery.put(keyId, id);
		step2.setAction(Action.INSERT);
		step2.setCollection(collection);
		step2.setQuery(insertQuery);
		step2.setDependsOn(step1.getId());
		stepList1.add(step2);
		
		// select after
		MongoStep step3 = new MongoStep();
		step3.setName("Step 3");
		step3.setServerId(server12.getId());
		step3.setServiceId(service2.getId());
		step3.setAction(Action.SELECT);
		step3.setCollection(collection);
		step3.setQuery(query);
		step3.setDependsOn(step2.getId());
		stepList1.add(step3);
		
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
		testSuite1.setEnvironmentId(env1.getId());
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);

		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}
	
	public static TestProject getMySQLTestPlan(){
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");
		
		// variables
		Variable variable = new Variable("response:$.[0].detalii", "name");
		List<Variable> variableList = new ArrayList<Variable>();
		variableList.add(variable);
		testProject.setVariableList(variableList);
		
		// construct service list
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
		// Server 3
		List<Server> serverList2 = new ArrayList<Server>();
		Server server21 = new Server();
		server21.setDescription("This is the first server of the second env");
		server21.setIp("localhost");
		server21.setName("Server 21");
		server21.setServices(serviceList4);
		serverList2.add(server21);
		
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
	
		// Environment 2
		Environment env2 = new Environment();
		env2.setName("Env 2");
		env2.setServers(serverList2);
		environmentList.add(env2);
		testProject.setEnvironmentList(environmentList);

		// construct test steps
		// test 3
		List<Step> stepList2 = new ArrayList<Step>();
		MySQLStep step3 = new MySQLStep();
		step3.setName("Step 3");
		step3.setServerId(server21.getId());
		step3.setServiceId(service4.getId());
		step3.addVariable(variable.getId());
		stepList2.add(step3);
		
		
		// construct test case list
		// test case 2		
		List<TestCase> testCaseList2 = new ArrayList<TestCase>();
		TestCase testCase2 = new TestCase();
		testCase2.setName("TC 1");
		testCase2.setStepList(stepList2);
		testCaseList2.add(testCase2);
		
		// construct test suite list
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();

		TestSuite testSuite2 = new TestSuite();
		testSuite2.setName("Test Suite 2");
		testSuite2.setEnvironmentId(env2.getId());
		testSuite2.setTestCaseList(testCaseList2);
		testSuiteList.add(testSuite2);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}
	
	public static TestProject getSOAPTestPlan() throws IOException{
		
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
		
		// set the variable list
		Variable variable = new Variable();
		variable.setName("name");
		variable.setType(VariableType.STRING);
		variable.setSelector("response://string[1]");
		List<Variable> projectVariableList = new ArrayList<Variable>();
		projectVariableList.add(variable);
		testProject.setVariableList(projectVariableList );

		// construct service list
		
		// Service 3
		List<Service> serviceList3 = new ArrayList<Service>();
		SoapService service3 = new SoapService();
		List<Authentication> authenticationList = new ArrayList<Authentication>();  
		Authentication auth = new Authentication();
		auth.setUsername("test");
		auth.setPassword("test");
		auth.setRole("test");
		authenticationList.add(auth);
		service3.setName("Service SOAP");
		service3.setPort("80");
		service3.setPath("/data/info.wso");
		service3.setWsdlURL("http://footballpool.dataaccess.eu/data/info.wso?wsdl");
		service3.setAuthenticationList(authenticationList);
		serviceList3.add(service3);
		
		// construct server list
		List<Server> serverList2 = new ArrayList<Server>();

		// Server 4
		Server server22 = new Server();
		server22.setDescription("This is the second server of the second env");
		server22.setIp("http://footballpool.dataaccess.eu");
		server22.setName("Server 22");
		server22.setServices(serviceList3);
		serverList2.add(server22);
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();

		// Environment 2
		Environment env2 = new Environment();
		env2.setName("Env 2");
		env2.setServers(serverList2);
		environmentList.add(env2);
		testProject.setEnvironmentList(environmentList);

		// construct test steps
		List<Step> stepList2 = new ArrayList<Step>();

		// test 4
		SoapStep step4 = new SoapStep();
		step4.setName("Step 4");
		step4.setServerId(server22.getId());
		step4.setServiceId(service3.getId());
		String request = new String(Files.readAllBytes(Paths.get("src/test/resources/SOAPRequest.xml")));
		step4.setRequest(request);
		step4.addVariable(variable.getId());
		stepList2.add(step4);
		
		// construct test case list
		// test case 2		
		List<TestCase> testCaseList2 = new ArrayList<TestCase>();
		TestCase testCase2 = new TestCase();
		testCase2.setName("TC 1");
		testCase2.setStepList(stepList2);
		testCaseList2.add(testCase2);
		
		// construct test suite list
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
		TestSuite testSuite2 = new TestSuite();
		testSuite2.setName("Test Suite 2");
		testSuite2.setEnvironmentId(env2.getId());
		testSuite2.setTestCaseList(testCaseList2);
		testSuiteList.add(testSuite2);
		
		testProject.setTestSuiteList(testSuiteList);
	
		return testProject;
	}

	public static TestProject getDependantStepsPlan() {
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");

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
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
		// Environment 1
		Environment env1 = new Environment();
		env1.setName("Env 1");
		env1.setServers(serverList1);
		
		environmentList.add(env1);
		testProject.setEnvironmentList(environmentList);

		// construct test steps
		// test 1
		List<Step> stepList1 = new ArrayList<Step>();
		RestStep restStep = new RestStep();
		restStep.setName("Step 1");
		restStep.setServerId(server11.getId());
		restStep.setServiceId(restService.getId());
		restStep.setPath("/customer/getCustomers");
		restStep.setMethod(RestMethod.GET);
		stepList1.add(restStep);
		// test 2
		MongoStep mongoStep = new MongoStep();
		mongoStep.setName("Step 2");
		mongoStep.setServerId(server12.getId());
		mongoStep.setServiceId(service2.getId());
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		query.put("name", "HAC");
		mongoStep.setAction(Action.SELECT);
		mongoStep.setCollection(collection);
		mongoStep.setQuery(query);
		mongoStep.setDependsOn(restStep.getId());
		stepList1.add(mongoStep);
		
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
		testSuite1.setEnvironmentId(env1.getId());
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}

	public static TestProject getDependantStepsNotBlockedPlan() {
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");

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
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
		// Environment 1
		Environment env1 = new Environment();
		env1.setName("Env 1");
		env1.setServers(serverList1);
		
		environmentList.add(env1);
		testProject.setEnvironmentList(environmentList);

		// construct test steps
		// test 1
		List<Step> stepList1 = new ArrayList<Step>();
		RestStep restStep = new RestStep();
		restStep.setName("Step 1");
		restStep.setServerId(server11.getId());
		restStep.setServiceId(restService.getId());
		restStep.setPath("/customer/getCustomers");
		restStep.setMethod(RestMethod.GET);
		stepList1.add(restStep);
		// test 2
		MongoStep mongoStep = new MongoStep();
		mongoStep.setName("Step 2");
		mongoStep.setServerId(server12.getId());
		mongoStep.setServiceId(service2.getId());
		String collection = "customer";
		HashMap<String, String> query = new HashMap<String, String>();
		query.put("name", "HAC");
		mongoStep.setAction(Action.SELECT);
		mongoStep.setCollection(collection);
		mongoStep.setQuery(query);
		mongoStep.setDependsOn(restStep.getId());
		stepList1.add(mongoStep);
		//test3
		MongoStep mongoStep2 = new MongoStep();
		mongoStep2.setName("Mongo Step 2");
		mongoStep2.setServerId(server12.getId());
		mongoStep2.setServiceId(service2.getId());
		mongoStep2.setCollection(collection);
		mongoStep2.setAction(Action.SELECT);
		mongoStep2.setQuery(query);
		stepList1.add(mongoStep2);
		
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
		testSuite1.setEnvironmentId(env1.getId());
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}

	public static TestProject getMockedRestProject() {
		
		TestProject testPlan = new TestProject();
		testPlan.setName("Rest Test Plan");
		
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
		restService.setStatus(ServiceStatus.MOCKED);
		String output = "mockedPath";
		Rule rule = new RestRule(InputType.PATH, "getCustomers", output);
		String output2 = "mockedMethod";
		Rule rule2 = new RestRule(InputType.METHOD, "PUT", output2);
		ArrayList<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(rule);
		ruleList.add(rule2);
		restService.setRuleList(ruleList);
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
		RestStep restStep = new RestStep();
		restStep.setName("Step 1");
		restStep.setServerId(server11.getId());
		restStep.setServiceId(restService.getId());
		restStep.setPath("/customer/getCustomers");
		restStep.setMethod(RestMethod.GET);
		stepList1.add(restStep);
		
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
		testSuite1.setEnvironmentId(env1.getId());
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
	
		testPlan.setTestSuiteList(testSuiteList);
		
		return testPlan;
	}

	public static TestProject getMockedRestSoapProject() throws IOException {
		
		TestProject testPlan = new TestProject();
		testPlan.setName("Mocked Test Plan");
		
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
		restService.setStatus(ServiceStatus.MOCKED);
		Rule rule = new RestRule(InputType.PATH, "getCustomers", "mockedOutput");
		Rule rule2 = new RestRule(InputType.METHOD, "PUT", "mockedMethod");
		ArrayList<Rule> ruleList = new ArrayList<Rule>();
		ruleList.add(rule);
		ruleList.add(rule2);
		restService.setRuleList(ruleList);
		serviceList1.add(restService);
		// Service 3
		List<Service> serviceList3 = new ArrayList<Service>();
		SoapService service3 = new SoapService();
		service3.setName("Service SOAP");
		service3.setPort("80");
		service3.setPath("/data/info.wso");
		service3.setWsdlURL("http://footballpool.dataaccess.eu/data/info.wso?wsdl");
		service3.setStatus(ServiceStatus.MOCKED);
		Rule soapRule = new SoapRule(com.wstester.model.SoapRule.InputType.REQUEST, new String(Files.readAllBytes(Paths.get("src/test/resources/SOAPRequest.xml"))), "Mocked response");
		ArrayList<Rule> soapRuleList = new ArrayList<Rule>();
		soapRuleList.add(soapRule);
		service3.setRuleList(soapRuleList);
		serviceList3.add(service3);
		
		// construct server list
		List<Server> serverList1 = new ArrayList<Server>();
		// Server 1
		Server server11 = new Server();
		server11.setDescription("This is the first server of the first env");
		server11.setIp("localhost");
		server11.setName("Server 11");
		server11.setServices(serviceList1);
		serverList1.add(server11);
		// Server 4
		Server server22 = new Server();
		server22.setDescription("This is the second server of the second env");
		server22.setIp("http://footballpool.dataaccess.eu");
		server22.setName("Server 22");
		server22.setServices(serviceList3);
		serverList1.add(server22);
		
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
		RestStep restStep = new RestStep();
		restStep.setName("Step 1");
		restStep.setServerId(server11.getId());
		restStep.setServiceId(restService.getId());
		restStep.setPath("/customer/getCustomers");
		restStep.setMethod(RestMethod.GET);
		stepList1.add(restStep);
		// test 4
		SoapStep step4 = new SoapStep();
		step4.setName("Step 4");
		step4.setServerId(server22.getId());
		step4.setServiceId(service3.getId());
		String request = new String(Files.readAllBytes(Paths.get("src/test/resources/SOAPRequest.xml")));
		step4.setRequest(request);
		stepList1.add(step4);
		
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
		testSuite1.setEnvironmentId(env1.getId());
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
	
		testPlan.setTestSuiteList(testSuiteList);
		
		return testPlan;
	}

	public static TestProject getMockedSoapProject() {
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");
		
		// construct asset list
		List<Asset> assetList = new ArrayList<Asset>();
		HashMap<Asset, AssetType> assetMap = new HashMap<Asset, AssetType>();
		Asset asset = new Asset();
		asset.setName("AssetFile.txt");
		asset.setPath("src/test/resources");
		assetList.add(asset);
		assetMap.put(asset, AssetType.BODY);
		testProject.setAssetList(assetList);
		
		// set the variable list
		Variable variable = new Variable("projectVar", VariableType.STRING, "selector");
		List<Variable> projectVariableList = new ArrayList<Variable>();
		projectVariableList.add(variable);
		testProject.setVariableList(projectVariableList );

		// construct service list
		
		// Service 3
		List<Service> serviceList3 = new ArrayList<Service>();
		SoapService service3 = new SoapService();
		service3.setName("Service SOAP");
		service3.setPort("80");
		service3.setPath("/data/info.wso");
		service3.setWsdlURL("http://footballpool.dataaccess.eu/data/info.wso?wsdl");
		service3.setStatus(ServiceStatus.MOCKED);
		ArrayList<Rule> ruleList = new ArrayList<Rule>();
		SoapRule rule = new SoapRule(com.wstester.model.SoapRule.InputType.REQUEST, asset, "Mocked Asset");
		ruleList.add(rule);
		service3.setRuleList(ruleList);
		serviceList3.add(service3);
		
		// construct server list
		List<Server> serverList2 = new ArrayList<Server>();

		// Server 4
		Server server22 = new Server();
		server22.setDescription("This is the second server of the second env");
		server22.setIp("http://footballpool.dataaccess.eu");
		server22.setName("Server 22");
		server22.setServices(serviceList3);
		serverList2.add(server22);
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();

		// Environment 2
		Environment env2 = new Environment();
		env2.setName("Env 2");
		env2.setServers(serverList2);
		environmentList.add(env2);
		testProject.setEnvironmentList(environmentList);

		// construct test steps
		List<Step> stepList2 = new ArrayList<Step>();

		// test 4
		SoapStep step4 = new SoapStep();
		step4.setName("Step 4");
		step4.setServerId(server22.getId());
		step4.setServiceId(service3.getId());
		step4.setAssetMap(assetMap);
		stepList2.add(step4);
		
		// construct test case list
		// test case 2		
		List<TestCase> testCaseList2 = new ArrayList<TestCase>();
		TestCase testCase2 = new TestCase();
		testCase2.setName("TC 1");
		testCase2.setStepList(stepList2);
		testCaseList2.add(testCase2);
		
		// construct test suite list
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();
		TestSuite testSuite2 = new TestSuite();
		testSuite2.setName("Test Suite 2");
		testSuite2.setEnvironmentId(env2.getId());
		testSuite2.setTestCaseList(testCaseList2);
		testSuiteList.add(testSuite2);
		
		testProject.setTestSuiteList(testSuiteList);
	
		return testProject;
	}

	public static TestProject getMockedMongoProject(){
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");
		
		// construct service list		
		// Service 2
		List<Service> serviceList2 = new ArrayList<Service>();
		MongoService service2 = new MongoService();
		service2.setName("Service Mongo");
		service2.setPort("27017");
		service2.setDbName("test");
		service2.setUser("appuser");
		service2.setPassword("apppass");
		service2.setStatus(ServiceStatus.MOCKED);
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
		step2.setServerId(server12.getId());
		step2.setServiceId(service2.getId());
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
		testSuite1.setEnvironmentId(env1.getId());
		testSuite1.setTestCaseList(testCaseList1);
		testSuiteList.add(testSuite1);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}
	
	public static TestProject getMockedMySQLProject(){
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");	

		// construct service list
		// Service 4
		List<Service> serviceList4 = new ArrayList<Service>();
		MySQLService mysqlService = new MySQLService();
		mysqlService.setName("Service MYSQL");
		mysqlService.setPort("3306");
		mysqlService.setDbName("test");
		mysqlService.setUser("appuser");
		mysqlService.setPassword("apppass");
		mysqlService.setStatus(ServiceStatus.MOCKED);
		List<Rule> ruleList = new ArrayList<Rule>();
		Rule rule = new MysqlRule(com.wstester.model.MysqlRule.InputType.OPERATION, "inputOperation", "mockedOperation");
		ruleList.add(rule );
		mysqlService.setRuleList(ruleList );
		serviceList4.add(mysqlService);
		
		// construct server list
		

		// Server 3
		List<Server> serverList2 = new ArrayList<Server>();
		Server server21 = new Server();
		server21.setDescription("This is the first server of the second env");
		server21.setIp("localhost");
		server21.setName("Server 21");
		server21.setServices(serviceList4);
		serverList2.add(server21);
		
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
	
		// Environment 2
		Environment env2 = new Environment();
		env2.setName("Env 2");
		env2.setServers(serverList2);
		environmentList.add(env2);
		testProject.setEnvironmentList(environmentList);

		// construct test steps
		// test 3
		List<Step> stepList2 = new ArrayList<Step>();
		MySQLStep step3 = new MySQLStep();
		step3.setName("Step 3");
		step3.setServerId(server21.getId());
		step3.setServiceId(mysqlService.getId());
		stepList2.add(step3);
		
		
		// construct test case list
		// test case 2		
		List<TestCase> testCaseList2 = new ArrayList<TestCase>();
		TestCase testCase2 = new TestCase();
		testCase2.setName("TC 1");
		testCase2.setStepList(stepList2);
		testCaseList2.add(testCase2);
		
		// construct test suite list
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();

		TestSuite testSuite2 = new TestSuite();
		testSuite2.setName("Test Suite 2");
		testSuite2.setEnvironmentId(env2.getId());
		testSuite2.setTestCaseList(testCaseList2);
		testSuiteList.add(testSuite2);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}
	
	public static TestProject getAssertTestProject() {
		
		TestProject testProject = new TestProject();
		testProject.setName("Test Project");
		
		// construct service list
		// Service 4
		List<Service> serviceList = new ArrayList<Service>();
		MySQLService mysqlService = new MySQLService();
		mysqlService.setName("Service MYSQL");
		mysqlService.setPort("3306");
		mysqlService.setDbName("test");
		mysqlService.setUser("appuser");
		mysqlService.setPassword("apppass");
		serviceList.add(mysqlService);
		
		// construct server list
		// Server 3
		List<Server> serverList = new ArrayList<Server>();
		Server server = new Server();
		server.setDescription("This is the first server of the second env");
		server.setIp("localhost");
		server.setName("Server 21");
		server.setServices(serviceList);
		serverList.add(server);
		
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
		// Environment 2
		Environment env = new Environment();
		env.setName("Env");
		env.setServers(serverList);
		environmentList.add(env);
		testProject.setEnvironmentList(environmentList);

		// construct test steps
		// test 3
		List<Step> stepList = new ArrayList<Step>();
		MySQLStep mysqlStep = new MySQLStep();
		mysqlStep.setName("Step");
		mysqlStep.setServerId(server.getId());
		mysqlStep.setServiceId(mysqlService.getId());
		mysqlStep.setOperation("SELECT detalii from angajati where detalii = 'popescu'");
		Assert azzert = new Assert();
		azzert.setExpected("[{detalii=popescu}]");
		List<Assert> assertList = new ArrayList<Assert>();
		assertList.add(azzert);
		mysqlStep.setAssertList(assertList);
		stepList.add(mysqlStep);
		
		
		// construct test case list
		// test case 2		
		List<TestCase> testCaseList2 = new ArrayList<TestCase>();
		TestCase testCase2 = new TestCase();
		testCase2.setName("TC 1");
		testCase2.setStepList(stepList);
		testCaseList2.add(testCase2);
		
		// construct test suite list
		List<TestSuite> testSuiteList = new ArrayList<TestSuite>();

		TestSuite testSuite2 = new TestSuite();
		testSuite2.setName("Test Suite 2");
		testSuite2.setEnvironmentId(env.getId());
		testSuite2.setTestCaseList(testCaseList2);
		testSuiteList.add(testSuite2);
		
		testProject.setTestSuiteList(testSuiteList);
		
		return testProject;
	}
}