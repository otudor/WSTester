package com.wstester.testFactory;

import com.wstester.elements.Dialog;
import com.wstester.main.MainLauncher;
import com.wstester.model.Environment;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

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
		
		IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not get the TestSuite!", MainLauncher.stage);
		}
		
		TestSuite testSuite = projectFinder.getTestSuiteById(testSuiteId);
		Environment environment = projectFinder.getEnvironmentById(testSuite.getEnvironmentId());
		TestProject testProject = projectFinder.getTestProject();
		
		name.clear();
		name.setText(testSuite.getName());
		
		environmentBox.getItems().clear();
		environmentBox.getItems().addAll(testProject.getEnvironmentList());
		environmentBox.getSelectionModel().select(environment);
	}
	
    public void saveTestSuite(ActionEvent actionEvent) {
    	
    	TestSuite testSuite = new TestSuite();
    	testSuite.setName(name.getText());
    	testSuite.setEnvironmentId(environmentBox.getValue().getId());
    	
		IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not get the TestSuite!", MainLauncher.stage);
		}
		
		projectFinder.setTestSuiteById(testSuite, testSuiteId);
	}
}