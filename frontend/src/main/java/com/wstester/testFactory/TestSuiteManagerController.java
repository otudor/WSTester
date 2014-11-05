package com.wstester.testFactory;

import java.net.URL;
import java.util.ResourceBundle;
import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ITestRunner;
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
    
    private TestSuiteListController tsListController;
    private TestSuiteController testSuiteController;
    private TestCaseController testCaseController;
    

    private MySQLStepController mysqlController;
    
    private MongoStepController mongoStepController;
    private SoapStepController soapStepController;
    private RestStepController restStepController;
    private EmptyTabController emptyTabController;
    
    private ResourceBundle resources;
    
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.resources = resources;
	}
	
    public void setTestSuiteListController( TestSuiteListController tsListController) {
        this.tsListController = tsListController;
    }
    
    public void setTestSuiteDetailController( TestSuiteController tsDetailController) {
        this.testSuiteController = tsDetailController;
    }

    public void setTestCaseDetailController( TestCaseController tcDetailController) {
        this.testCaseController = tcDetailController;
    }
    
    public void setMongoStepController( MongoStepController mongoStepController) {
        this.mongoStepController = mongoStepController;
    }
    
    public void setSoapStepController( SoapStepController soapStepController) {
        this.soapStepController = soapStepController;
    }
    
    public void setRestStepController( RestStepController restStepController) {
        this.restStepController = restStepController;
    }
    
    public void setMySQLStepController (MySQLStepController mySQLStepController) {
    	this.mysqlController = mySQLStepController;
    }
    
    public void setEmptyTabController(EmptyTabController emptyTabController) {
    	this.emptyTabController = emptyTabController;
	}
    
    public Parent getView() {
        return root;
    }

    public void loadTestSuites() {
    	tsListController.loadSuites();
    	tsListController.loadTreeItems();
        contentArea.setLeft( tsListController.getView());
    }

    public void showTestSuite( String tsUID) {
    	testSuiteController.setTestSuite( tsUID);
        contentArea.setCenter( testSuiteController.getView());
    }
        
    public void showEmptyTabController() {
    	emptyTabController.setEmptyTabController();
        contentArea.setCenter( emptyTabController.getView());
    }
    
    public void showTestCase( String tcUID) {
    	testCaseController.setTestCase( tcUID);
        contentArea.setCenter( testCaseController.getView());
    }
    
    public void showMySQLStep(String stepId) {
    	mysqlController.setStep(stepId);
    	contentArea.setCenter(mysqlController.getNode());
    }
    
    public void showMongoStep( String sUID) {
    	mongoStepController.setMongoStep(sUID);
        contentArea.setCenter( mongoStepController.getView());
    }
    
    public void showSoapStep( String sUID) {
    	soapStepController.setSoapStep(sUID);
        contentArea.setCenter( soapStepController.getView());
    }
    
    public void showRestStep(String sUID) {

    	restStepController.setStep(sUID);
        contentArea.setCenter(restStepController.getView());
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


