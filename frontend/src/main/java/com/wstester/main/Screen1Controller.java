package com.wstester.main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

import com.wstester.model.TestProject;
import com.wstester.services.impl.TestProjectActions;
import com.wstester.util.MainConstants;
import com.wstester.util.UtilityTool;

/**
 * 
 * @author lvasile
 * TODO: add java doc   && rename class
 */
public class Screen1Controller implements Initializable, ControlledScreen {

    ScreensController myController;
    public TestProject testproject;
   
    public TestProject getTestproject() {
		return testproject;
	}

	public void setTestproject(TestProject testproject) {
		this.testproject = testproject;
	}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
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
    	myController.setScreen(ScreensFramework.screen2ID);
    	TestProjectActions actions = new TestProjectActions();
    	try {
			testproject = actions.open("src/main/resources/testProject/Output.xml");
			Group root = new Group();
			FXMLLoader fxmlLoader = new FXMLLoader();	        
			AnchorPane frame = fxmlLoader.load(getClass().getResource("/fxml/main/WsTesterMain.fxml").openStream());

			UtilityTool.addEntity(MainConstants.TESTPROJECT, testproject);
			
	      	root.getChildren().add(frame);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	 
    	
       
    }
}
