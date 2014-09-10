package com.wstester.testFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.wstester.model.Environment;
import com.wstester.model.MySQLService;
import com.wstester.model.MySQLStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestSuite;

public class TestSuiteService
{
    //private long nextId = 0;
    private Map<String, TestSuite> tsList;

    public TestSuiteService()
    {
        this.tsList = new HashMap<String, TestSuite>();

        TestSuite ts1 = createTestSuite( "Test Suite");
        ts1.setEnvironment( new Environment("Environment de test"));
        List<TestCase> tcList = new ArrayList<TestCase>();
		TestCase tc1 = new TestCase();
		tc1.setName("Test Case");
		List<Step> stpList = new ArrayList<Step>();
		//step
		List<Server> srvList = new ArrayList<Server>();
		Server server = new Server();
		server.setIp("localhost");
		List<Service> srviceList = new ArrayList<Service>();
		MySQLService service = new MySQLService();
		service.setDbName("test");
		service.setPort("3306");
		service.setUser("appuser");
		service.setPassword("apppass");		
		MySQLStep mysqlStep = new MySQLStep();
		mysqlStep.setName("MySQL Step");
		mysqlStep.setServer(server);
		mysqlStep.setService(service);		
		mysqlStep.setOperation("select * from Angajati where id='4'");
		
		MySQLStep mysqlStep2 = new MySQLStep();
		mysqlStep2.setName("MySQL Step");
		mysqlStep2.setServer(server);
		mysqlStep2.setService(service);		
		mysqlStep2.setOperation("select * from Angajati where id='2'");
		mysqlStep2.setDependsOn(mysqlStep.getID());
		
		stpList.add(mysqlStep);
		stpList.add(mysqlStep2);
		tc1.setStepList(stpList);
		tcList.add( tc1);
		ts1.setTestCaseList( tcList);		
    }

    public TestSuite createTestSuite(String name)
    {
    	TestSuite ts = new TestSuite();
    	ts.setName( name);
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
    
    public TestCase addTestCaseForTestSuite( String tsUID)
    {
    	TestCase result = null;
    	TestSuite ts = tsList.get( tsUID);
    	
    	if( ts != null)
    	{
    		List<TestCase> tcList = ts.getTestCaseList();
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
    

    
    /*public void removeEnvironmentById( String uId)
    {
    	tsList.remove( uId);
    }
    
    public Server getServerByUID( String serverUID)
    {
    	Server result = null;

		for (Map.Entry<String, Environment> entry : tsList.entrySet())
		{
		    //System.out.println(entry.getKey() + "/" + entry.getValue());
    		List<Server> serverList = ((Environment)entry.getValue()).getServers();
    		if ( serverList != null && !serverList.isEmpty())
    		{
    			for (Server server: serverList)
    				if (server.getID() == serverUID)
    				{
    					result = server;
    					break;
    				}
    		}
    	}
    	
    	return result;
    	//return new Server( "Server 3", "0909.05.05.05", "description server 3");
    }
    
    public Service getServiceByUID (String serverUID, String serviceUID)
    {
    	Service result = null;
    	for (Map.Entry<String, Environment> entry : tsList.entrySet())
		{
		    //System.out.println(entry.getKey() + "/" + entry.getValue());
    		List<Server> serverList = ((Environment)entry.getValue()).getServers();
    		if ( serverList != null && !serverList.isEmpty())
    		{
    			for (Server server: serverList)
    				if (server.getID() == serverUID)
    				{
    					List<Service> serviceList = server.getServices();
    					if ( serviceList != null && !serviceList.isEmpty())
    					{
    						for (Service service: serviceList)
    						{
    		    				if (service.getID() == serviceUID)
    		    				{
    		    					result = service;
    		    					return result;
    		    				}
    						}
    					}
    			}
    		}
   		}
    	
    	return result;
    }*/
}