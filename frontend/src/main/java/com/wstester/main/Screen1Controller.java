package com.wstester.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.wstester.model.TestProject;
import com.wstester.model.TestUtils;
import com.wstester.services.impl.TestProjectActions;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;


public class Screen1Controller implements Initializable, ControlledScreen {

    ScreensController myController;
    TestProject testproject;
    
    public TestProject getTestproject() {
		return testproject;
	}

	public void setTestproject(TestProject testproject) {
		this.testproject = testproject;
	}

	/**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

     @FXML
    private void goToScreen2(ActionEvent event){
    	
    	 testproject= new TestProject();
       myController.setScreen(ScreensFramework.screen2ID);
    }
    
    @FXML
    private void goToScreen3(ActionEvent event){
    	 TestProjectActions actions = new TestProjectActions();
    	try {
			testproject = actions.open("src/main/resources/testProject/Output.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	
       myController.setScreen(ScreensFramework.screen2ID);
    }
}
