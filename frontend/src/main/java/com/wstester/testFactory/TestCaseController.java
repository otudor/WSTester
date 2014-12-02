package com.wstester.testFactory;

import com.wstester.model.TestCase;
import com.wstester.util.TestProjectService;

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
    	TestProjectService service = new TestProjectService();
    	TestCase testCase = service.getTestCase(id);
    	name.setText(testCase.getName());
    }
    
    public void saveTestCase(ActionEvent e) {
    	
    	TestCase testCase = new TestCase();
    	testCase.setName(name.getText());
    	
    	TestProjectService service = new TestProjectService();
    	service.setTestCaseById(testCase, testCaseId);
    }
}