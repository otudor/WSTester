package com.wstester.testFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class ResponseTabController {
	@FXML private Node rootPane;
	@FXML private StackPane treePane;
	
	private TestSuiteService tsService;
	private TestSuiteManagerController tsMainController;
	
	public void setTestSuiteService( TestSuiteService tsService)
    {
        this.tsService = tsService;
    }

    public void setTestSuiteManagerController(TestSuiteManagerController tsMainController)
    {
        this.tsMainController = tsMainController;
    }
    
    public Node getView()
    {
        return rootPane;
    }

    public void setResponseTabController()
    {
    	
    }
}
