package com.wstester.testFactory;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import com.wstester.elements.Dialog;
import com.wstester.model.Action;
import com.wstester.model.MongoStep;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class MongoStepController {
	
    @FXML 
    private TextField mongoCollection;
    @FXML 
    private ComboBox<Action> mongoAction;
    @FXML 
    private TextArea mongoQuery;
    @FXML
	private StepController stepController;
    
    private String mongoId = null;            

    public void setStep(String mongoId, List<String> higherTestList){
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
        stepController.setCommonFields(higherTestList);  
        
        
        if (step instanceof MongoStep) {
    		if(((MongoStep) step).getCollection() != null){
    			mongoCollection.setText((String)((MongoStep) step).getCollection());
    		}
    		
    		if (((MongoStep) step).getQuery() != null){
    			mongoQuery.setText(((MongoStep) step).getQuery());
	    	}
        
    		mongoAction.getItems().addAll(Action.values());
    		if (((MongoStep) step).getAction() != null){      	
    			mongoAction.setValue(((MongoStep) step).getAction());
    		}									
        }
    }

	private void clearFields() {
		
		mongoCollection.setText("");
		mongoQuery.setText("");	
		mongoAction.getItems().clear();
		mongoAction.setValue(null);
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
    	newStep.setDependsOn(stepController.getDependsOn());
    	newStep.setCollection(mongoCollection.getText());
    	newStep.setAction(mongoAction.getValue());
    	newStep.setQuery(mongoQuery.getText());
    	newStep.setDataProvider(stepController.hasDataProvider());
    	
    	//TODO: make a method in the project finder to save the step 
    	projectFinder.getStepById(mongoId).copyFrom(newStep);
	}
}