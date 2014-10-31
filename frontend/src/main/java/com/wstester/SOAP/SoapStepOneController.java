package com.wstester.SOAP;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import javax.annotation.PostConstruct;

import org.datafx.controller.FXMLController;
import org.datafx.controller.flow.action.LinkAction;

@FXMLController(value="/fxml/SOAP/stepOne.fxml", title = "Wizard: Step 1")
public class SoapStepOneController extends SoapAbstractWizardController {
    
	@FXML
	private ComboBox<String> importType;
	@FXML
	private ComboBox<String> importSubtype;
	
	
	@FXML
    @LinkAction(SoapStepTwoController.class)
    private Button nextButton;
	

	
	

   

	@PostConstruct
    public void init() {
        getBackButton().setDisable(true);
    }
    
    @FXML
	private void initialize() {
		this.setImport();
		this.setRest();
//		this.moveToNext();
	}
  


	public void setImport(){
	   importType.setItems(FXCollections.observableArrayList("SOAP", "Rest"));
	  // importType.getChildrenUnmodifiable().add(importType);
   }

    private void setRest() {
    	importType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

    	    @Override
    	    public void changed(ObservableValue observable, String oldValue, String newValue) {
    	        if (newValue.equals("SOAP")){
    	        	importSubtype.setItems(FXCollections.observableArrayList("WSDL"));
    	        }
    	        if (newValue.equals("Rest")){
    	        	importSubtype.setItems(FXCollections.observableArrayList("MANUAL" , "SWAGGER" , "WADL"));
    	        }
    	    }
    	});
	
	}
    
//    private void moveToNext() {
//    	importSubtype.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//
//    	    @Override
//    	    public void changed(ObservableValue observable, String oldValue, String newValue) {
//    	        if (newValue.equals("WSDL")){
////    	        	Flow flow = new Flow(RestStepOneController.class).withLink(RestStepOneController.class, "next", RestStepTwoController.class);
//    	        }
//    	        if (newValue.equals("MANUAL")){
//    	        	importSubtype.setItems(FXCollections.observableArrayList("MANUAL" , "SWAGGER" , "WADL"));
//    	        }
//    	        if (newValue.equals("SWAGGER")){
//    	        	importSubtype.setItems(FXCollections.observableArrayList("WSDL"));
//    	        }
//    	        if (newValue.equals("WADL")){
//    	        	importSubtype.setItems(FXCollections.observableArrayList("WSDL"));
//    	        }
//    	    }
//    	});
		
	}
    
    
    
    
