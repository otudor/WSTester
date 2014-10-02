package com.wstester.testFactory;

import com.wstester.model.Environment;
import com.wstester.model.TestSuite;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TestSuiteController 
{
	@FXML private Node rootEnvDetails;
	@FXML private TextField tsName;
	@FXML private ComboBox<Environment> envBox;
		
	private TestSuiteService tsService;
	
	private TestSuiteManagerController tsManagerController;
	private Environment environment;
	private String uid;

	public void setTestSuiteService(TestSuiteService tsService)	{
		this.tsService = tsService;
	}

	public void setMainPresenter(TestSuiteManagerController tsManagerController)	
	{
		this.tsManagerController = tsManagerController;
	}

	public Node getView()	
	{
		return rootEnvDetails;
	}

	public void setTestSuite(final String tsUID)	{
		tsName.setText("");
		uid = tsUID;
		TestSuite ts = tsService.getTestSuite( tsUID);
		tsName.setText( ts.getName());
		envBox.setItems(FXCollections.observableArrayList(tsService.getEnvironmentList()));
		envBox.setValue(ts.getEnvironment());
		envBox.getSelectionModel().selectedItemProperty().addListener( new
        		ChangeListener<Environment>() {
        	public void changed(ObservableValue ov, Environment value, Environment new_value) {
        		  ts.setEnvironment(new_value); 
        		  environment = new_value;
        			}
        });
	}
	    
    public void saveTestSuite(ActionEvent e) {
    	TestSuite testSuite = new TestSuite();
    	testSuite.setName(tsName.getText());
    	testSuite.setEnvironment(environment);
    	tsService.setTestSuiteByUID(testSuite, uid);
		tsService.saveTestSuite();
	}
}