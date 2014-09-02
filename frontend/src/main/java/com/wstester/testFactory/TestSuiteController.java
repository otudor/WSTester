package com.wstester.testFactory;

import com.wstester.model.TestSuite;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;

public class TestSuiteController 
{
	@FXML private Node rootEnvDetails;
	@FXML private TextField tsNameField;
	private TestSuiteService tsService;
	private TestSuiteManagerController tsManagerController;

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
		tsNameField.setText("");

		TestSuite env = tsService.getTestSuite( tsUID);
		tsNameField.setText( env.getName());
	}
}