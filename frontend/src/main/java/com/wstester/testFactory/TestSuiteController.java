package com.wstester.testFactory;

import com.wstester.elements.Dialog;
import com.wstester.main.MainLauncher;
import com.wstester.model.Environment;
import com.wstester.model.TestSuite;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;
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
		Environment environment = null;
		try {
			IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
			environment = projectFinder.getEnvironmentById(testSuite.getEnvironmentId());
		} catch (Exception e) {
			Dialog.errorDialog("Could not get the TestSuite environment!", MainLauncher.stage);
		}
		
		name.clear();
		name.setText(testSuite.getName());
		
		environmentBox.getItems().clear();
		environmentBox.getItems().addAll(testProjectService.getEnvironmentList());
		environmentBox.getSelectionModel().select(environment);
	}
	
    public void saveTestSuite(ActionEvent e) {
    	
    	TestSuite testSuite = new TestSuite();
    	testSuite.setName(name.getText());
    	testSuite.setEnvironmentId(environmentBox.getValue().getId());
    	
    	TestProjectService testProjectService = new TestProjectService();
    	testProjectService.setTestSuiteById(testSuite, testSuiteId);
	}
}