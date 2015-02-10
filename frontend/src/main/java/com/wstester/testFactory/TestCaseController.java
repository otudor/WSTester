package com.wstester.testFactory;

import com.wstester.elements.Dialog;
import com.wstester.main.MainLauncher;
import com.wstester.model.TestCase;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class TestCaseController {
	
    @FXML
    private Node rootTestCase;
    @FXML
    private TextField name;
    private String testCaseId;

    public void setTestCaseId(String id) {
    
    	this.testCaseId = id;
    	IProjectFinder projectFinder = null;
    	try {
    		projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
    	} catch (Exception e) {
    		e.printStackTrace();
    		Dialog.errorDialog("Could not find the TestCase. Please try again!", MainLauncher.stage);
    	}
    	TestCase testCase = projectFinder.getTestCaseById(id);
    	name.setText(testCase.getName());
    }
    
    public void saveTestCase(ActionEvent actionEvent) {
    	
    	TestCase testCase = new TestCase();
    	testCase.setName(name.getText());
    	
    	// save the testCase
    	IProjectFinder projectFinder = null;
    	try {
    		projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
    	} catch (Exception e) {
    		e.printStackTrace();
    		Dialog.errorDialog("Could not find the TestCase. Please try again!", MainLauncher.stage);
    	}
    	projectFinder.setTestCaseById(testCase, testCaseId);
    }
}