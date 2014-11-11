package com.wstester.testFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import com.wstester.model.MySQLStep;
import com.wstester.model.Step;
import com.wstester.util.TestProjectService;

public class MySQLStepController{
	
	@FXML
	private ResponseController responseController;
	@FXML
	private AnchorPane rootMySQLStep;
	@FXML
	private StepController stepController;
	@FXML
	private TextField mysqlOperation;
	
	private TestProjectService testProjectService;
	private String stepId;
		
	public void setStep(String stepId){
		this.stepId = stepId;
		
    	clearFields();
		TestProjectService testProjectService = new TestProjectService();
		Step step = testProjectService.getStep(stepId);
		
        stepController.setStep(stepId);
        stepController.setCommonFields();
        responseController.setResponse(step);
	}
	
    private void clearFields() {
    	mysqlOperation.setText("");
	}

	@Deprecated
	public Node getView() {
		
		testProjectService = new TestProjectService();
        
        stepController.setStep(stepId);
        stepController.setCommonFields();
        
        
    	Step step = testProjectService.getStep(stepId);

    	if(step instanceof MySQLStep){
    		if(((MySQLStep) step).getOperation() != null){
    			mysqlOperation.setText(((MySQLStep) step).getOperation());
    		}
    	}
    	
		return rootMySQLStep;
	}
	
}