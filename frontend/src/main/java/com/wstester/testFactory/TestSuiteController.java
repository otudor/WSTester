package com.wstester.testFactory;

import com.wstester.model.Environment;
import com.wstester.model.TestSuite;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TestSuiteController {
	
	@FXML
	private Node rootEnvDetails;
	@FXML 
	private TextField tsName;
	@FXML 
	private ComboBox<Environment> envBox;
		
	private TestProjectService testProjectService;
	private Environment environment;
	private String uid;

	public Node getView() {
		return rootEnvDetails;
	}

	public void setTestSuite(final String tsUID) {
		
		testProjectService = new TestProjectService();
		this.uid = tsUID;
		TestSuite testSuite = testProjectService.getTestSuite(tsUID);
		tsName.clear();
		tsName.setText(testSuite.getName());
		environment = testSuite.getEnvironment();
		
		envBox.getItems().clear();
		envBox.getItems().addAll(testProjectService.getEnvironmentList());
//		envBox.setValue(environment);	
		envBox.getSelectionModel().select(environment);
	}
	
	@FXML
	public void handleEnvironmentChangeAction(){
		
		Environment selectedEnvironment = envBox.getSelectionModel().getSelectedItem();
		this.environment = selectedEnvironment; 
	}
	
    public void saveTestSuite(ActionEvent e) {
    	
    	
    	TestSuite testSuite = testProjectService.getTestSuite(uid);
    	testSuite.setName(tsName.getText());
    	testSuite.setEnvironment(environment);
    	testProjectService.setTestSuiteByUID(testSuite, uid);
	}
}