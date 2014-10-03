package com.wstester.testFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;

public class EmptyTabController {
	
	@FXML public Node rootEmptyTab;
	
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
	        return rootEmptyTab;
	    }

		public void setEmptyTabController() {
			// TODO Auto-generated method stub
			
		}

}
