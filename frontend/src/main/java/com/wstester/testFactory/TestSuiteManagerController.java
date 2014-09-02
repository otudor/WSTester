package com.wstester.testFactory;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

public class TestSuiteManagerController
{
    @FXML private Parent root;
    @FXML private BorderPane contentArea;

    private TestSuiteListController tsListController;
    private TestSuiteController tsDetailController;
    private TestCaseController tcDetailController;

    public void setTestSuiteListController( TestSuiteListController tsListController)
    {
        this.tsListController = tsListController;
    }

    public void setTestSuiteDetailController( TestSuiteController tsDetailController)
    {
        this.tsDetailController = tsDetailController;
    }

    public void setTestCaseDetailController( TestCaseController tcDetailController)
    {
        this.tcDetailController = tcDetailController;
    }
    
    public Parent getView()
    {
        return root;
    }

    public void loadEnvironments()
    {
    	tsListController.search();
    	tsListController.loadTreeItems();
        contentArea.setLeft( tsListController.getView());        
    }

    public void showTestSuiteDetail( String envUID)
    {
    	tsDetailController.setTestSuite( envUID);
        contentArea.setCenter( tsDetailController.getView());
    }
    
    public void showTestCaseDetail( String tcUID)
    {
    	tcDetailController.setTestCase( tcUID);
        contentArea.setCenter( tcDetailController.getView());
    }
    
    public String getFirstEnvironment()
    {
    	return tsListController.getFirstEnv();
    }

}