package com.wstester.testFactory;

import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ITestRunner;
import com.wstester.util.MainConstants;
import com.wstester.util.UtilityTool;

import javafx.event.ActionEvent;
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
    private MongoStepController mongoStepController;
    private SoapStepController soapStepController;
    private RestStepController restStepController;
    
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
    
    public void setMongoStepController( MongoStepController mongoStepController)
    {
        this.mongoStepController = mongoStepController;
    }
    
    public void setSoapStepController( SoapStepController soapStepController)
    {
        this.soapStepController = soapStepController;
    }
    
    public void setRestStepController( RestStepController restStepController)
    {
        this.restStepController = restStepController;
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
    
    public void showMongoStep( String sUID)
    {
    	mongoStepController.setMongoStep(sUID);
        contentArea.setCenter( mongoStepController.getView());
    }
    
    public void showSoapStep( String sUID)
    {
    	soapStepController.setSoapStep(sUID);
        contentArea.setCenter( soapStepController.getView());
    }
    
    public void showRestStep( String sUID)
    {
    	restStepController.setRestStep(sUID);
        contentArea.setCenter( restStepController.getView());
    }
        
    public void runTestSuite( ActionEvent event) throws Exception
    {
    	TestProject testProject = new TestProject();
		testProject.setTestSuiteList(tsListController.getTestSuiteList());
	
		ITestRunner testRunner = ServiceLocator.getInstance().lookup(ITestRunner.class, testProject);
		testRunner.run(testProject);
		
		ExecutionUpdate execUpd = new ExecutionUpdate(tsListController);
		
		//btnRun.setText("Running...");
		
		execUpd.updateRunStatus();
		
		//btnRun.setDisable(true);
		
//		btnRun.setDisable(false);
	}
}


