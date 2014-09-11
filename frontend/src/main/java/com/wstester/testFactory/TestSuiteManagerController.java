package com.wstester.testFactory;

import com.wstester.actions.TestRunner;
import com.wstester.model.TestProject;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class TestSuiteManagerController
{
    @FXML private Parent root;
    @FXML private BorderPane contentArea;
    @FXML public Button btnRun;

    private TestSuiteListController tsListController;
    private TestSuiteController testSuiteController;
    private TestCaseController testCaseController;
    private MySQLStepController mysqlStepController;

	@FXML
	private void initialize() 
	{
		//btnRun.addEventHandler(ActionEvent.ANY, new BtnHandler());
		//btnRun.addEventHandler(MyEvent.CEVA, new MyEventHandler());
	}
	
    public void setTestSuiteListController( TestSuiteListController tsListController)
    {
        this.tsListController = tsListController;
    }

    public void setTestSuiteDetailController( TestSuiteController tsDetailController)
    {
        this.testSuiteController = tsDetailController;
    }

    public void setTestCaseDetailController( TestCaseController tcDetailController)
    {
        this.testCaseController = tcDetailController;
    }
    
    public void setMySQLStepController( MySQLStepController mysqlStepController)
    {
        this.mysqlStepController = mysqlStepController;
    }
    
    public Parent getView()
    {
        return root;
    }

    public void loadTestSuites()
    {
    	tsListController.loadSuites();
    	tsListController.loadTreeItems();
        contentArea.setLeft( tsListController.getView());        
    }

    public void showTestSuite( String tsUID)
    {
    	testSuiteController.setTestSuite( tsUID);
        contentArea.setCenter( testSuiteController.getView());
    }
    
    public void showTestCase( String tcUID)
    {
    	testCaseController.setTestCase( tcUID);
        contentArea.setCenter( testCaseController.getView());
    }
    
    public void showMySQLStep( String sUID)
    {
    	mysqlStepController.setMySQLStep(sUID);
        contentArea.setCenter( mysqlStepController.getView());
    }
    
    public String getFirstEnvironment()
    {
    	return tsListController.getFirstEnv();
    }

    public void runTestSuite( ActionEvent event) throws Exception
    {
		TestProject testProject = new TestProject();
		testProject.setTestSuiteList(tsListController.getTestSuiteList());
		TestRunner testRunner = new TestRunner(testProject);
		testRunner.run(testProject);
		//testRunner.run();
		
		ExecutionUpdate execUpd = new ExecutionUpdate(tsListController);
		
		//btnRun.setText("Running...");
		
		execUpd.updateRunStatus();
		
		//btnRun.setDisable(true);
		
//		btnRun.setDisable(false);
	}
}


