package com.wstester.testFactory;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class TestSuiteManagerController
{
    @FXML private Parent root;
    @FXML private BorderPane contentArea;

    private TestSuiteListController tsListController;
    private TestSuiteController testSuiteController;
    private TestCaseController testCaseController;
    private MySQLStepController mysqlStepController;

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
    	tsListController.search();
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

}