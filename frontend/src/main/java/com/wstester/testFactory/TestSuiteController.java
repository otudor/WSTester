package com.wstester.testFactory;

import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.services.impl.TestRunner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class TestSuiteController 
{
	@FXML private Node rootEnvDetails;
	@FXML private TextField tsName;
	@FXML private TextField txtEnvName;
	//@FXML private Button btnRun;
	
	private TestSuiteService tsService;
	
	private TestSuiteManagerController tsManagerController;

	public void setTestSuiteService(TestSuiteService tsService)	{
		this.tsService = tsService;
	}

	public void setMainPresenter(TestSuiteManagerController tsManagerController)	
	{
		this.tsManagerController = tsManagerController;
	}

	public Node getView()	
	{
		return rootEnvDetails;
	}

	public void setTestSuite(final String tsUID)	{
		tsName.setText("");
		TestSuite ts = tsService.getTestSuite( tsUID);
		tsName.setText( ts.getName());
		txtEnvName.setText( ts.getEnvironment().getName());
	}
	
    public void runTestSuite( ActionEvent event) throws Exception
    {
    	//tsService.getFirstTestSuite();
		TestProject testProject = new TestProject();
		testProject.setTestSuiteList(tsService.getTestSuites());
		TestRunner testRunner = new TestRunner(testProject);
//		testRunner.setTestProject(testProject);
		
//		testRunner.run();
		
		//TestSuiteListController.updateRunStatus();
		
//		ExecutionUpdate execUpd = new ExecutionUpdate();
//		execUpd.updateRunStatus();
		
		//Response mysqlResponse = 
		
		//JSONArray result = new JSONArray(mysqlResponse.getContent());

		//System.out.println(result);
	}
}