package com.wstester.testFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.wstester.model.MySQLStep;
import com.wstester.model.Step;
import com.wstester.util.TestProjectService;

public class MySQLStepController{
	
	@FXML
	private StepController stepController;
	@FXML
	private TextField mysqlOperation;
	
	private String stepId;
		
	public void setStep(String stepId){
		this.stepId = stepId;
		
    	clearFields();
		TestProjectService testProjectService = new TestProjectService();
		Step step = testProjectService.getStep(stepId);
		
        stepController.setStep(stepId);
        stepController.setCommonFields();
        
        if(step instanceof MySQLStep){
    		if(((MySQLStep) step).getOperation() != null){
    			mysqlOperation.setText(((MySQLStep) step).getOperation());
    		}
    	}
	}
	
    private void clearFields() {
    	mysqlOperation.setText("");
	}	
    
    // called when the save button is clicked
	public void saveMysql(ActionEvent e) {
    	
    	TestProjectService testProjectService = new TestProjectService();
    	MySQLStep step = new MySQLStep();
    	
    	step.setServer(stepController.getServer());
    	step.setService(stepController.getService());
    	step.setName(stepController.getName());
    	step.setOperation(mysqlOperation.getText());
		
    	testProjectService.setStepByUID(step, stepId);
	}
}