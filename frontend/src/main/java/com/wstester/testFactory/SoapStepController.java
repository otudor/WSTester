package com.wstester.testFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

import com.wstester.elements.Dialog;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class SoapStepController {

    @FXML 
    private TextArea soapRequest;
    @FXML
	private StepController stepController;
    @FXML
    private String stepId = null;  
    
    public void setStep(String stepId) {
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
        stepController.setCommonFields();  	
        
        if(step instanceof SoapStep) {
    		if(((SoapStep) step).getRequest() != null) {
    			soapRequest.setText((String)((SoapStep) step).getRequest());
    		}
    	}
	}

	private void clearFields() {
		soapRequest.setText("");
	}
	     
	public void saveSoap(ActionEvent actionEvent) {
		
		IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
		
    	SoapStep newStep = new SoapStep();    	
    	newStep.setServerId(stepController.getServer().getId());
    	newStep.setServiceId(stepController.getService().getId());
    	newStep.setName(stepController.getName());   
    	newStep.setRequest(soapRequest.getText());
    	
    	//TODO: replace this method to one from projectFinder
    	projectFinder.getStepById(stepId).copyFrom(newStep);
	}
}