package com.wstester.testFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.MongoStep;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.util.MainConstants;
import com.wstester.util.UtilityTool;

public class TestSuiteService
{
    //private long nextId = 0;
    private Map<String, TestSuite> tsList;
    private TestProject testProject;

    public TestSuiteService()
    {
        this.tsList = new HashMap<String, TestSuite>();
        testProject = (TestProject) UtilityTool.getEntity(MainConstants.TESTPROJECT);
        if(testProject.getTestSuiteList()!=null)
        {
        	for(TestSuite entry:testProject.getTestSuiteList())
        	{
        		tsList.put(entry.getID(),entry);
        	}
        }       
    }

    public TestSuite createTestSuite(String name)
    {
    	TestSuite ts = new TestSuite();
    	ts.setName( name);
    	//ts.setEnvironment(envList.get(0));
    	tsList.put( ts.getID(), ts);
        return ts;
    }

    public List<TestSuite> getTestSuites()
    {
        List<TestSuite> matches = new ArrayList<TestSuite>();
        matches.addAll( tsList.values());

        return matches;
    }

    public TestSuite getTestSuite( String uID)
    {
        return tsList.get( uID);
    }
    
    public TestSuite getFirstTestSuite()
    {
    	TestSuite result = null;
    	if ( !tsList.isEmpty())
    		result = tsList.get( tsList.keySet().toArray()[0]);
        
    	return result;
    }
    
    public MySQLStep addMySQLStepforTestCase(String tcUID) 
    {
		TestCase tCase = null;
		MySQLStep result = null;
		
		for (Map.Entry<String, TestSuite> entry : tsList.entrySet()) 
		{
			List<TestCase> tcList = ((TestSuite) entry.getValue()).getTestCaseList();
			
			if (tcList != null && !tcList.isEmpty()) 
			{
				for ( TestCase tc : tcList)
					if (tc.getID() == tcUID) 
					{
						tCase = tc;
						break;
					}
				
				if (tCase != null)
					break;
			}
		}

		if( tCase != null)
		{
			result = new MySQLStep();
			result.setName("MySQL Step");
			tCase.addStep(result);
		}
		
		return result;
	}
    
    public MongoStep addMongoStepforTestCase(String tcUID) 
    {
		TestCase tCase = null;
		MongoStep result = null;
		
		for (Map.Entry<String, TestSuite> entry : tsList.entrySet()) 
		{
			List<TestCase> tcList = ((TestSuite) entry.getValue()).getTestCaseList();
			
			if (tcList != null && !tcList.isEmpty()) 
			{
				for ( TestCase tc : tcList)
					if (tc.getID() == tcUID) 
					{
						tCase = tc;
						break;
					}
				
				if (tCase != null)
					break;
			}
		}

		if( tCase != null)
		{
			result = new MongoStep();
			result.setName("Mongo Step");
			tCase.addStep(result);
		}
		
		return result;
	}
    
    public SoapStep addSoapStepforTestCase(String tcUID) 
    {
		TestCase tCase = null;
		SoapStep result = null;
		
		for (Map.Entry<String, TestSuite> entry : tsList.entrySet()) 
		{
			List<TestCase> tcList = ((TestSuite) entry.getValue()).getTestCaseList();
			
			if (tcList != null && !tcList.isEmpty()) 
			{
				for ( TestCase tc : tcList)
					if (tc.getID() == tcUID) 
					{
						tCase = tc;
						break;
					}
				
				if (tCase != null)
					break;
			}
		}

		if( tCase != null)
		{
			result = new SoapStep();
			result.setName("Soap Step");
			tCase.addStep(result);
		}
		
		return result;
	}
    
    public RestStep addRestStepforTestCase(String tcUID) 
    {
		TestCase tCase = null;
		RestStep result = null;
		
		for (Map.Entry<String, TestSuite> entry : tsList.entrySet()) 
		{
			List<TestCase> tcList = ((TestSuite) entry.getValue()).getTestCaseList();
			
			if (tcList != null && !tcList.isEmpty()) 
			{
				for ( TestCase tc : tcList)
					if (tc.getID() == tcUID) 
					{
						tCase = tc;
						break;
					}
				
				if (tCase != null)
					break;
			}
		}

		if( tCase != null)
		{
			result = new RestStep();
			result.setName("Rest Step");
			tCase.addStep(result);
		}
		
		return result;
	}
    
    public TestCase addTestCaseForTestSuite( String tsUID)
    {
    	TestCase result = null;
    	TestSuite ts = tsList.get( tsUID);
    		
    	if( ts != null)
    	{
    		List <TestCase>tcList = ts.getTestCaseList();
    		result = new TestCase();
        	result.setName("Test Case");
        	tcList.add(result);
    		//ts.setTestCaseList(tcList);
    	}
    	
    	return result;
    }
    
    public void removeTestCase( String tsUID, String tcUID)
    {
    	TestSuite ts = tsList.get( tsUID);
    	
    	if( ts != null)
    	{
    		List<TestCase> tcList = ts.getTestCaseList();
    		if ( tcList != null && !tcList.isEmpty())
    			for (TestCase tc: tcList)
    				if ( tc.getID() == tcUID)
    				{
    					ts.getTestCaseList().remove(tc);
    					break;
    				}
    	}
    }
    
    public Step getStep( String stepUID)
    {
    	if( tsList != null && !tsList.isEmpty())
    		for (Map.Entry<String, TestSuite> tSuite : tsList.entrySet()) 
    		{
    			List<TestCase> tcList = ((TestSuite) tSuite.getValue()).getTestCaseList();
    			if (tcList != null && !tcList.isEmpty())
    				for (TestCase tc: tcList)
    				{
    					List<Step> stepList = tc.getStepList();
    					if( stepList != null && !stepList.isEmpty())
    						for( Step stp: stepList)
    							if (stp.getID() == stepUID)
									return stp;    								
    				}
    		}
    	
    	return null;
    }
        
    public void setMySQLStepByUID(MySQLStep src, String stepUID) {
		for (Map.Entry<String, TestSuite> entry : tsList.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<TestCase> caseList = ((TestSuite) entry.getValue())
					.getTestCaseList();
			if (caseList != null && !caseList.isEmpty()) 
			{
				for (TestCase testCase : caseList)
				{
					List<Step> stepList = testCase.getStepList();
						if (stepList != null && !stepList.isEmpty())
						{
							for (Step step : stepList) 
							{
								if (step.getID() == stepUID) 
								{
									MySQLStep mySQL = new MySQLStep();
									mySQL = (MySQLStep) step;
									mySQL.setOperation(src.getOperation());
									mySQL.setName(src.getName());
									mySQL.setService(src.getService());
									mySQL.setServer(src.getServer());
									mySQL.setExecutionList(src.getExecutionList());
									mySQL.setAssertList(src.getAssertList());
									mySQL.setAssetList(src.getAssetList());
									mySQL.setDependsOn(src.getDependsOn());
									mySQL.setVariableList(src.getVariableList());
									step = (Step) mySQL;
								}			
							}
						}
				}
			}
		}
	}
    
    
    public void setMongoStepByUID(MongoStep src, String stepUID) {
		for (Map.Entry<String, TestSuite> entry : tsList.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<TestCase> caseList = ((TestSuite) entry.getValue())
					.getTestCaseList();
			if (caseList != null && !caseList.isEmpty()) 
			{
				for (TestCase testCase : caseList)
				{
					List<Step> stepList = testCase.getStepList();
						if (stepList != null && !stepList.isEmpty())
						{
							for (Step step : stepList) 
							{
								if (step.getID() == stepUID) 
								{
									MongoStep mongo = new MongoStep();
									mongo = (MongoStep) step;
									mongo.setAction(src.getAction());
									mongo.setAssertList(src.getAssertList());
									mongo.setAssetList(src.getAssetList());
									mongo.setCollection(src.getCollection());
									mongo.setDependsOn(src.getDependsOn());
									mongo.setExecutionList(src.getExecutionList());
									mongo.setName(src.getName());
									mongo.setQuery(src.getQuery());
									mongo.setServer(src.getServer());
									mongo.setService(src.getService());
									mongo.setVariableList(src.getVariableList());
									step = (Step) mongo;
								}			
							}
						}
				}
			}
		}
	}
    
    public void setRestStepByUID(RestStep src, String stepUID) {
		for (Map.Entry<String, TestSuite> entry : tsList.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<TestCase> caseList = ((TestSuite) entry.getValue())
					.getTestCaseList();
			if (caseList != null && !caseList.isEmpty()) 
			{
				for (TestCase testCase : caseList)
				{
					List<Step> stepList = testCase.getStepList();
						if (stepList != null && !stepList.isEmpty())
						{
							for (Step step : stepList) 
							{
								if (step.getID() == stepUID) 
								{
									RestStep rest = new RestStep();
									rest = (RestStep) step;
									rest.setAssertList(src.getAssertList());
									rest.setAssetList(src.getAssetList());
									rest.setContentType(src.getContentType());
									rest.setCookie(src.getCookie());
									rest.setDependsOn(src.getDependsOn());
									rest.setExecutionList(src.getExecutionList());
									rest.setHeader(src.getHeader());
									rest.setMethod(src.getMethod());
									rest.setName(src.getName());
									rest.setPath(src.getPath());
									rest.setQuery(src.getQuery());
									rest.setRequest(src.getRequest());
									rest.setServer(src.getServer());
									rest.setService(src.getService());
									rest.setVariableList(src.getVariableList());
 									rest = (RestStep) step;
									step = (Step) rest;
								}			
							}
						}
				}
			}
		}
	}
    
    public void setSoapStepByUID(SoapStep src, String stepUID) {
		for (Map.Entry<String, TestSuite> entry : tsList.entrySet()) {
			// System.out.println(entry.getKey() + "/" + entry.getValue());
			List<TestCase> caseList = ((TestSuite) entry.getValue())
					.getTestCaseList();
			if (caseList != null && !caseList.isEmpty()) 
			{
				for (TestCase testCase : caseList)
				{
					List<Step> stepList = testCase.getStepList();
						if (stepList != null && !stepList.isEmpty())
						{
							for (Step step : stepList) 
							{
								if (step.getID() == stepUID) 
								{
									SoapStep soap = new SoapStep();
									soap = (SoapStep) step;
									soap.setAssertList(src.getAssertList());
									soap.setAssetList(src.getAssetList());
									soap.setDependsOn(src.getDependsOn());
									soap.setExecutionList(src.getExecutionList());
									soap.setName(src.getName());
									soap.setRequest(src.getRequest());
									soap.setServer(src.getServer());
									soap.setService(src.getService());
									soap.setVariableList(src.getVariableList());
									
									step = (Step) soap;
								}			
							}
						}
				}
			}
		}
	}
    
    public void saveTestSuite()
	{	
		List<TestSuite> list = new ArrayList<>();
		for(TestSuite entry : tsList.values() )
		{
			list.add(entry);
		}
		((TestProject)UtilityTool.getEntity(MainConstants.TESTPROJECT)).setTestSuiteList(list);
	}
     
}