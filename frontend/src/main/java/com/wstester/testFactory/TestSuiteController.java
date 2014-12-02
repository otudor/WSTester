package com.wstester.testFactory;

import com.wstester.model.Environment;
import com.wstester.model.TestSuite;
import com.wstester.util.TestProjectService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TestSuiteController {
	
	@FXML 
	private TextField name;
	@FXML 
	private ComboBox<Environment> environmentBox;
		
	private String testSuiteId;

	public void setTestSuite(String testSuiteId) {
		
		this.testSuiteId = testSuiteId;
		
		TestProjectService testProjectService = new TestProjectService();
		TestSuite testSuite = testProjectService.getTestSuite(testSuiteId);
		
		name.clear();
		name.setText(testSuite.getName());
		
		environmentBox.getItems().clear();
		environmentBox.getItems().addAll(testProjectService.getEnvironmentList());
		environmentBox.getSelectionModel().select(testSuite.getEnvironment());
	}
	
    public void saveTestSuite(ActionEvent e) {
    	
    	TestSuite testSuite = new TestSuite();
    	testSuite.setName(name.getText());
    	testSuite.setEnvironment(environmentBox.getValue());
    	
    	TestProjectService testProjectService = new TestProjectService();
    	testProjectService.setTestSuiteById(testSuite, testSuiteId);
	}
}