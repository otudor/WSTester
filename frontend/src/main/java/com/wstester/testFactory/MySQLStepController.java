package com.wstester.testFactory;

import com.wstester.model.MySQLStep;
import com.wstester.model.Step;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MySQLStepController{
	
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
	}
	
	public Node getNode() {
		
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