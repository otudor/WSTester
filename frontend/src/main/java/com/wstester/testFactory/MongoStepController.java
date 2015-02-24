package com.wstester.testFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import com.wstester.elements.Dialog;
import com.wstester.model.MongoStep;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class MongoStepController {
    @FXML 
    private Node rootMongoStep;
    @FXML 
    private TextField mongoCollection;
    @FXML 
    private ComboBox <String> mongoAction;
    @FXML 
    private TextField mongoQuery;

    @FXML
	private StepController stepController;
    private String mongoId = null;  
          
    @FXML
    private void initialize() {   	 
    }
    
    public void setStep(String mongoId){
		this.mongoId = mongoId;
		
    	clearFields();
    	Step step = null;
		try {
			IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
			step = projectFinder.getStepById(mongoId);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
		
        stepController.setStep(mongoId);
        stepController.setCommonFields();  	
	}

	private void clearFields() {
		mongoCollection.setText("");
		mongoQuery.setText("");	
	}
	     
public void saveMongo(ActionEvent actionEvent) {
		IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
				
    	MongoStep newStep = new MongoStep();    	
    	newStep.setServerId(stepController.getServer().getId());
    	newStep.setServiceId(stepController.getService().getId());
    	newStep.setName(stepController.getName());
    	newStep.setCollection(mongoCollection.getText());
    	projectFinder.getStepById(mongoId).copyFrom(newStep);
	}
}
    
    
    
    
    
    
    
    
    
    
    
