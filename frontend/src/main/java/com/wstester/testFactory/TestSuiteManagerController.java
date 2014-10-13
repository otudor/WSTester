package com.wstester.testFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.cxf.wsdl.TService;
import org.datafx.controller.FXMLController;
import org.springframework.beans.propertyeditors.ResourceBundleEditor;

import com.javafx.main.Main;
import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ITestRunner;
import com.wstester.util.MainConstants;
import com.wstester.util.UtilityTool;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class TestSuiteManagerController implements Initializable{
    @FXML private Parent root;
    @FXML private BorderPane contentArea;
    @FXML public Button btnRun;
    
    private TestProjectService tsService;
    private TestSuiteListController tsListController;
    private TestSuiteController testSuiteController;
    private TestCaseController testCaseController;
    private StepController stepController;
    

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
    
    public void setMySQLStepController (MySQLStepController mySQLStepController){
    	this.mysqlController = mySQLStepController;
    }
    public void setEmptyTabController(EmptyTabController emptyTabController) {
    	this.emptyTabController = emptyTabController;
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
        
    public void showEmptyTabController()
    {
    	emptyTabController.setEmptyTabController();
        contentArea.setCenter( emptyTabController.getView());
    }
    
    public void showTestCase( String tcUID)
    {
    	testCaseController.setTestCase( tcUID);
        contentArea.setCenter( testCaseController.getView());
    }
    
    public void showMySQLStep(String stepId) {
    	
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(Main.class.getResource("/fxml/TestFactory/MySQLStep.fxml"));
//        try {
//			loader.load(getClass().getResourceAsStream("/fxml/TestFactory/MySQLStep.fxml"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//        
//        mysqlController = (MySQLStepController) loader.getController();
    	mysqlController.setStep(stepId);
    
    	contentArea.setCenter(mysqlController.getNode());
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
//    	 FXMLLoader loader = new FXMLLoader();
//         loader.setLocation(Main.class.getResource("/fxml/TestFactory/RestStep.fxml"));
//         try {
// 			loader.load(getClass().getResourceAsStream("/fxml/TestFactory/RestStep.fxml"));
// 		} catch (IOException e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 		}
//        
//        restStepController = (RestStepController)loader.getController();
    	restStepController.setStep(sUID);
        contentArea.setCenter( restStepController.getView());
    }
        
    public void runTestSuite( ActionEvent event) throws Exception
    {
    	TestProject testProject = new TestProject();
		testProject.setTestSuiteList(tsListController.getTestSuiteList());
	
		ITestRunner testRunner = ServiceLocator.getInstance().lookup(ITestRunner.class, testProject);
		testRunner.run(testProject);
//		testRunner.getResponse(tsService.getStep(), timeout);
		
		ExecutionUpdate execUpd = new ExecutionUpdate(tsListController);
		
		//btnRun.setText("Running...");
		
		execUpd.updateRunStatus();
		
		//btnRun.setDisable(true);
		
//		btnRun.setDisable(false);
	}

	
}


