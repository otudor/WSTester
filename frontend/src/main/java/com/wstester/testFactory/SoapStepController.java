package com.wstester.testFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.wstester.elements.Dialog;
import com.wstester.model.Asset;
import com.wstester.model.Environment;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapStep;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import com.wstester.elements.Dialog;
import com.wstester.model.Environment;
import com.wstester.model.MongoStep;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MySQLStep;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class SoapStepController {
    @FXML 
    private Node rootSoapStep;
    @FXML 
    private TextField soapOperation;
    @FXML
	private StepController stepController;
    @FXML
    private String soapID = null;  
    
    
    @FXML
    private void initialize() {
    	
    }
    
    public void setStep(String soapID){
		this.soapID = soapID;
		
    	clearFields();
    	Step step = null;
		try {
			IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
			step = projectFinder.getStepById(soapID);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
		
        stepController.setStep(soapID);
        stepController.setCommonFields();  	
	}

	private void clearFields() {
		soapOperation.setText("");
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
    	newStep.setName(soapOperation.getText());
    	projectFinder.getStepById(soapID).copyFrom(newStep);
	}
}
    
    
    
    
    
    
    
    
    
    
    
