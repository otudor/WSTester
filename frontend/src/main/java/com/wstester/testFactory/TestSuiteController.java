package com.wstester.testFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.wstester.actions.TestRunner;
import com.wstester.model.Environment;
import com.wstester.model.MySQLStep;
import com.wstester.model.Response;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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