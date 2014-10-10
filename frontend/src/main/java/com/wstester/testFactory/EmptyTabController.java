package com.wstester.testFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;

public class EmptyTabController {
	
	@FXML public Node rootEmptyTab;
	
	private TestProjectService tsService;
    private TestSuiteManagerController tsMainController;
	
	   public void setTestSuiteService( TestProjectService tsService)
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
