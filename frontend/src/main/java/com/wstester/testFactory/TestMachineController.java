package com.wstester.testFactory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.wstester.elements.Dialog;
import com.wstester.elements.TestSuiteTreeImplementation;
import com.wstester.main.MainLauncher;
import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestSuite;
import com.wstester.util.MainConstants;
import com.wstester.util.TestProjectService;

public class TestMachineController implements Initializable {

	@FXML
	private Node root;
	@FXML
	private TreeView<Object> treeView;
	@FXML
	private Button runButton;
	@FXML
	private BorderPane contentArea;

	private List<TestSuite> testSuiteList;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		TestProjectService testProjectService = new TestProjectService();
		testSuiteList = testProjectService.getTestSuites();
		loadTreeItems();
	}

	@Deprecated
	public Node getView() {
		return root;
	}
	
	public TreeView<Object> getTreeView() {
		return this.treeView;
	}

	public void selectTestSuite(String testSuiteId) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.TEST_SUITE.toString()));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			Dialog.errorDialog("Can't open the test factory window. Please try again!", MainLauncher.stage);
		}
		TestSuiteController testSuiteController = loader.<TestSuiteController> getController();

		testSuiteController.setTestSuite(testSuiteId);
		contentArea.setCenter(testSuiteController.getView());
	}

	public void selectTestCase(String testCaseId) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.TEST_CASE.toString()));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			Dialog.errorDialog("Can't open the test factory window. Please try again!", MainLauncher.stage);
		}
		TestCaseController testCaseController = loader.<TestCaseController> getController();

		testCaseController.setTestCaseId(testCaseId);
		contentArea.setCenter(loader.getRoot());
	}

	public void selectStep(String stepId) {

		TestProjectService testProjectService = new TestProjectService();
		Step step = testProjectService.getStep(stepId);

		if (step instanceof MongoStep) {
			setMongoStep(stepId);
		} else if (step instanceof RestStep) {
			setRestStep(stepId);
		} else if (step instanceof MySQLStep) {
			setMysqlStep(stepId);
		} else if (step instanceof SoapStep) {
			setSoapStep(stepId);
		}
	}

	private void setMongoStep(String stepId) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.MONGO_STEP.toString()));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			Dialog.errorDialog("Can't open the test factory window. Please try again!", MainLauncher.stage);
		}
		MongoStepController mongoStepController = loader.<MongoStepController> getController();

		mongoStepController.setStep(stepId);
		contentArea.setCenter(loader.getRoot());
	}

	private void setRestStep(String stepId) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.REST_STEP.toString()));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			Dialog.errorDialog("Can't open the test factory window. Please try again!", MainLauncher.stage);
		}
		RestStepController restStepController = loader.<RestStepController> getController();

		restStepController.setStep(stepId);
		contentArea.setCenter(loader.getRoot());
	}

	private void setMysqlStep(String stepId) {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.MYSQL_STEP.toString()));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			Dialog.errorDialog("Can't open the test factory window. Please try again!", MainLauncher.stage);
		}
		MySQLStepController mysqlStepController = loader.<MySQLStepController>getController();
		
		mysqlStepController.setStep(stepId);
		contentArea.setCenter(mysqlStepController.getView());
	}
	
	private void setSoapStep(String stepId) {
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.SOAP_STEP.toString()));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			Dialog.errorDialog("Can't open the test factory window. Please try again!", MainLauncher.stage);
		}
		SoapStepController soapStepController = loader.<SoapStepController>getController();
		
		soapStepController.setStep(stepId);
		contentArea.setCenter(loader.getRoot());
	}
	
	public void loadTreeItems() {

		TreeItem<Object> root = new TreeItem<Object>("treeRoot");
		root.setExpanded(true);

		if (testSuiteList != null) {
			for (TestSuite testSuite : testSuiteList) {
				
				Node testSuiteIcon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.TEST_SUITE_ICON.toString())));
				TreeItem<Object> testSuiteItem = new TreeItem<Object>(testSuite, testSuiteIcon);

				if (testSuite.getTestCaseList() != null) {
					for (TestCase testCase : testSuite.getTestCaseList()) {

						Node testCaseIcon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.TEST_CASE_ICON.toString())));
						TreeItem<Object> testCaseItem = new TreeItem<Object>(testCase, testCaseIcon);

						if (testCase.getStepList() != null) {
							for (Step step : testCase.getStepList()) {
								
								Node stepIcon = null;
								if (step instanceof MySQLStep) {
									stepIcon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.MYSQL_STEP_ICON.toString())));
								}
								else if (step instanceof MongoStep) {
									stepIcon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.MONGO_STEP_ICON.toString())));
								}
								else if (step instanceof SoapStep) {
									stepIcon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.SOAP_STEP_ICON.toString())));
								}
								else if (step instanceof RestStep) {
									stepIcon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.REST_STEP_ICON.toString())));
								}
								TreeItem<Object> stepItem = new TreeItem<Object>(step, stepIcon);
								testCaseItem.getChildren().add(stepItem);
							}
						}
						testSuiteItem.getChildren().add(testCaseItem);
					}
				}
				root.getChildren().add(testSuiteItem);
			}
		}
		
		treeView.setShowRoot(false);
		treeView.setRoot(root);
		treeView.setEditable(false);
		treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		treeView.setCellFactory(new Callback<TreeView<Object>, TreeCell<Object>>() {
			@Override
			public TreeCell<Object> call(TreeView<Object> treeView) {
				return new TestSuiteTreeImplementation();
			}
		});
	}

	@FXML
	public void createTestSuite(ActionEvent event) {

		TestSuite testSuite = new TestSuite();
		testSuite.setName("New TestSuite");
		TestProjectService testProjectService = new TestProjectService();
		testProjectService.addTestSuite(testSuite);

		Node icon = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_environment.png")));
		TreeItem<Object> node = new TreeItem<Object>(testSuite, icon);
		treeView.getRoot().getChildren().add(node);
		treeView.setEditable(true);
		treeView.getSelectionModel().select(node);
		treeView.getFocusModel().focusNext();
		treeView.edit(node);
		selectTestSuite(testSuite.getID());
	}
}