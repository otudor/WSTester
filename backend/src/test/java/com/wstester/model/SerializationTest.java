package com.wstester.model;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.wstester.actions.TestProjectActions;
import com.wstester.model.Assert;
import com.wstester.model.Asset;
import com.wstester.model.Environment;
import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.ServiceType;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SerializationTest {

	private static String filePath = "src/test/resources/Output.xml";
	private static File file = new File(filePath);
	private static TestProject testPlanBefore;
	private static TestProject testPlanAfter;
	
	@Test
	public void firstTestToXML() throws IOException{
		
		// construct test plan
		testPlanBefore = new TestProject();
		testPlanBefore.setName("Test Plan");
		
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
		testPlanBefore.setAssetList(assetList);

		// construct service list
		// Service 1
		List<Service> serviceList1 = new ArrayList<Service>();
		Service service1 = new Service();
		service1.setName("Service Rest");
		service1.setPort("8080");
		service1.setType(ServiceType.REST);
		serviceList1.add(service1);
		
		// Service 2
		List<Service> serviceList2 = new ArrayList<Service>();
		Service service2 = new Service();
		service2.setName("Service Mongo");
		service2.setPort("8081");
		service2.setType(ServiceType.MONGO);
		serviceList2.add(service2);
		
		// Service 3
		List<Service> serviceList3 = new ArrayList<Service>();
		Service service3 = new Service();
		service3.setName("Service SOAP");
		service3.setPort("6142");
		service3.setType(ServiceType.SOAP);
		serviceList3.add(service3);
		
		// Service 4
		List<Service> serviceList4 = new ArrayList<Service>();
		Service service4 = new Service();
		service4.setName("Service MYSQL");
		service4.setPort("1160");
		service4.setType(ServiceType.MYSQL);
		serviceList4.add(service4);
		
		// construct server list
		List<Server> serverList1 = new ArrayList<Server>();
		// Server 1
		Server server11 = new Server();
		server11.setDescription("This is the first server of the first env");
		server11.setIp("10.0.0.1");
		server11.setName("Server 11");
		server11.setServices(serviceList1);
		serverList1.add(server11);
		// Server 2
		Server server12 = new Server();
		server12.setDescription("This is the second server of the first env");
		server12.setIp("10.0.0.2");
		server12.setName("Server 12");
		server12.setServices(serviceList2);
		serverList1.add(server12);

		// Server 3
		List<Server> serverList2 = new ArrayList<Server>();
		Server server21 = new Server();
		server21.setDescription("This is the first server of the second env");
		server21.setIp("10.0.0.1");
		server21.setName("Server 21");
		server21.setServices(serviceList3);
		serverList2.add(server21);
		// Server 4
		Server server22 = new Server();
		server22.setDescription("This is the second server of the second env");
		server22.setIp("10.0.0.2");
		server22.setName("Server 22");
		server22.setServices(serviceList4);
		serverList2.add(server22);
		
		// construct environment list
		List<Environment> environmentList = new ArrayList<Environment>();
		// Environment 1
		Environment env1 = new Environment();
		env1.setName("Env 1");
		env1.setServers(serverList2);
		// Environment 2
		Environment env2 = new Environment();
		env2.setName("Env 2");
		env2.setServers(serverList2);
		environmentList.add(env1);
		environmentList.add(env2);
		testPlanBefore.setEnvironmentList(environmentList);

		// construct test steps
		// test 1
		List<Step> stepList1 = new ArrayList<Step>();
		RestStep step1 = new RestStep();
		step1.setName("Step 1");
		step1.setServer(server11);
		step1.setService(service1);
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
		stepList1.add(step2);
		
		// test 3
		List<Step> stepList2 = new ArrayList<Step>();
		MySQLStep step3 = new MySQLStep();
		step3.setName("Step 3");
		step3.setServer(server21);
		step3.setService(service3);
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
		step4.setService(service4);
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
		testPlanBefore.setTestSuiteList(testSuiteList);
		testPlanBefore.setTestSuiteList(testSuiteList);

		TestProjectActions actions = new TestProjectActions();
		actions.save(filePath, testPlanBefore);
	}
	
	@Test()
	public void secondTestFromXML() throws IOException {

		TestProjectActions actions = new TestProjectActions();
        testPlanAfter = actions.open(filePath);
        
        assertEquals(testPlanBefore, testPlanAfter);
	}
	
	@AfterClass
	public static void delete(){
		
		if(file.delete()){
			System.out.println(file.getName() + " is deleted!");
		}else{
			System.out.println("Delete operation is failed.");
		}
	}

}
