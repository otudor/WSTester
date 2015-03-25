package com.wstester.testFactory;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import com.wstester.elements.Dialog;
import com.wstester.model.MySQLStep;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class MySQLStepController{
	
	@FXML
	private StepController stepController;
	@FXML
	private TextField mysqlOperation;
	
	private String stepId;
		
	public void setStep(String stepId, List<String> higherTestList){
		this.stepId = stepId;
		
    	clearFields();
    	Step step = null;
		try {
			IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
			step = projectFinder.getStepById(stepId);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
		
        stepController.setStep(stepId);
        stepController.setCommonFields(higherTestList);
        
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
	public void saveMysql(ActionEvent actionEvent) {
    	
		// get the step before the save
		IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
		
		// construct the step after save
    	MySQLStep newStep = new MySQLStep();
    	
    	newStep.setServerId(stepController.getServer() == null ? null : stepController.getServer().getId());
    	newStep.setServiceId(stepController.getService() == null ? null : stepController.getService().getId());
    	newStep.setName(stepController.getName());
    	newStep.setDependsOn(stepController.getDependsOn());
    	newStep.setOperation(mysqlOperation.getText());
    	newStep.setDataProvider(stepController.hasDataProvider());
    	
    	//TODO: add in projectFinder an operation setStepById
    	projectFinder.getStepById(stepId).copyFrom(newStep);
	}
}