package com.wstester.testFactory;

import java.net.URL;
import java.util.ResourceBundle;

import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ITestRunner;
import com.wstester.util.TestProjectService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class TestSuiteManagerController implements Initializable{
	
    @FXML
    private Parent root;
    @FXML
    private BorderPane contentArea;
    @FXML
    public Button btnRun;
    
    private TestMachineController tsListController;

    
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	
    public void setTestSuiteListController( TestMachineController tsListController) {
        this.tsListController = tsListController;
    }

        
    public void runTestSuite( ActionEvent event) throws Exception {
    	TestProjectService testProjectService = new TestProjectService();
    	TestProject testProject = testProjectService.getTestProject();
	
		ITestRunner testRunner = ServiceLocator.getInstance().lookup(ITestRunner.class, testProject);
		testRunner.run(testProject);
		
		ExecutionUpdate execUpd = new ExecutionUpdate(tsListController);
		
		execUpd.updateRunStatus();
	}
}


