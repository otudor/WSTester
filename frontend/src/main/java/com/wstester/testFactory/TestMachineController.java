package com.wstester.testFactory;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.wstester.elements.Dialog;
import com.wstester.main.MainLauncher;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestProject;
import com.wstester.model.TestSuite;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ITestRunner;
import com.wstester.util.MainConstants;
import com.wstester.util.TestProjectService;

public class TestMachineController implements Initializable {

	@FXML
	private Node root;
	@FXML
	private TreeView<Object> treeView;
	@FXML
	private BorderPane contentArea;

	private List<TestSuite> testSuiteList;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		TestProjectService testProjectService = new TestProjectService();
		testSuiteList = testProjectService.getTestSuites();
		loadTreeItems();
	}
	
	public TreeView<Object> getTreeView() {
		return this.treeView;
	}

	private void selectTestSuite(String testSuiteId) {

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

	private void selectTestCase(String testCaseId) {

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

	private void selectStep(String stepId) {

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
	
	private void loadTreeItems() {

		TreeItem<Object> rootItem = new TreeItem<Object>("treeRoot");
		rootItem.setExpanded(true);

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
				rootItem.getChildren().add(testSuiteItem);
			}
		}
		
		treeView.setShowRoot(false);
		treeView.setRoot(rootItem);
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

		Node icon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.TEST_SUITE_ICON.toString())));
		TreeItem<Object> node = new TreeItem<Object>(testSuite, icon);
		treeView.getRoot().getChildren().add(node);
		treeView.setEditable(true);
		treeView.getSelectionModel().select(node);
		treeView.getFocusModel().focusNext();
		treeView.edit(node);
		selectTestSuite(testSuite.getID());
	}
	
	@FXML
	public void runTests(ActionEvent event){
		
    	TestProjectService testProjectService = new TestProjectService();
    	TestProject testProject = testProjectService.getTestProject();
		
		try {
			ITestRunner testRunner = ServiceLocator.getInstance().lookup(ITestRunner.class, testProject);
			testRunner.run(testProject);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("The test couldn't be run. Please try again!", MainLauncher.stage);
		}
		
		ExecutionUpdate execUpd = new ExecutionUpdate();
		execUpd.updateRunStatus();
	}
	
	public void updateTree() {
		
	}
	
	private class TestSuiteTreeImplementation extends TreeCell<Object> {

		public TestSuiteTreeImplementation() {

			// events when the user clicks on different levels of the tree view
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {

					if ((event.getClickCount() == 1) && (event.getButton() == MouseButton.PRIMARY)) {
						if (getItem() != null) {

							if (getItem() instanceof TestSuite) {
								selectTestSuite(((TestSuite) getItem()).getID());
							} else if (getItem() instanceof TestCase) {
								selectTestCase(((TestCase) getItem()).getID());
							} else if (getItem() instanceof Step) {
								selectStep(((Step) getItem()).getID());
							}
						}
					}
				}
			});
		}

		@Override
		public void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
				setContextMenu(null);
			} else {
				// if the item type is step add a special graphic if the last execution passed
				if (getItem() instanceof Step) {
					setGraphic(createStepGraphic(item));
				}
				// else just take the previous graphic
				else {
					setText(getItem() == null ? "" : getItem().toString());
					setGraphic(getTreeItem().getGraphic());
				}
				
				// construct the context menu for each level
				if (getItem() != null)
					if (getItem() instanceof TestSuite) {
						TestSuite testSuite = (TestSuite) getItem();
						this.setContextMenu(createTestSuiteContextMenu(testSuite.getID()));
					} 
					else if (getItem() instanceof TestCase) {
						TestCase testCase = (TestCase) getItem();
						this.setContextMenu(createTestCaseContextMenu(testCase.getID()));
					} 
					else if (getItem() instanceof Step) {
						Step step = (Step) getItem();
						this.setContextMenu(createTestStepContextMenu(step.getID()));
					}
			}
		}

		// this method adds to the step cell a special graphic indicating a success/failure
		// TODO: add a imagine for failure
		public HBox createStepGraphic(Object item) {
			HBox hbox = new HBox();
			hbox.setPadding(new Insets(0, 0, 0, 0));
			hbox.setSpacing(5);

			Label lblNodeName = new Label(getItem() == null ? "" : getItem().toString());
			hbox.getChildren().add(getTreeItem().getGraphic());
			hbox.getChildren().addAll(lblNodeName);

			Execution execution = ((Step) getItem()).getLastExecution();
			if (execution != null) {
				ImageView successImagine = null;
				if (execution.getResponse().getStatus() == ExecutionStatus.PASSED)
					successImagine = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.STEP_PASSED_ICON.toString())));

				hbox.getChildren().addAll(successImagine);
			}

			return hbox;
		}
		
		public ContextMenu createTestSuiteContextMenu(String testSuiteId) {
			
			final ContextMenu contextMenu = new ContextMenu();
			MenuItem addTestCaseMenu = new MenuItem("Add Test Case");
			MenuItem removeTestSuiteMenu = new MenuItem("Remove Test Suite");
			contextMenu.getItems().addAll(addTestCaseMenu, removeTestSuiteMenu);

			addTestCaseMenu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					TreeView<Object> treeView = getTreeView();
					TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
					if (item == null){
						return;
					}

					TestCase testCase = new TestCase();
					testCase.setName("New Test Case");
					TestProjectService testProjectService = new TestProjectService();
					testProjectService.addTestCaseForTestSuite(testCase, testSuiteId);

					Node icon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.TEST_CASE_ICON.toString())));
					TreeItem<Object> tcNode = new TreeItem<>(testCase, icon);
					item.getChildren().add(tcNode);
					treeView.getSelectionModel().select(tcNode);
					selectTestCase(testCase.getID());
				}
			});

			removeTestSuiteMenu.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					
					TreeView<Object> treeView = getTreeView();
					TreeItem<Object> selectedItem = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
					if (selectedItem == null)
						return;

					TestProjectService testProjectService = new TestProjectService();
					testProjectService.removeTestSuite(testSuiteId);
					
					selectedItem.getParent().getChildren().remove(selectedItem);
					contentArea.setCenter(null);
				}
			});

			return contextMenu;
		}

		public ContextMenu createTestStepContextMenu(String stepId) {
			
			final ContextMenu contextMenu = new ContextMenu();
			MenuItem removeStep = new MenuItem("Remove Step");
			contextMenu.getItems().addAll(removeStep);

			removeStep.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					TreeView<Object> treeView = getTreeView();
					TreeItem<Object> selectedItem = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
					if (selectedItem == null) {
						return;
					}
					TestProjectService testProjectService = new TestProjectService();
					testProjectService.removeTestStep(stepId);
					
					selectedItem.getParent().getChildren().remove(selectedItem);
					contentArea.setCenter(null);
				}
			});
			return contextMenu;
		}

		public ContextMenu createTestCaseContextMenu(String testCaseId) {
			
			final ContextMenu contextMenu = new ContextMenu();
			MenuItem addMysqlItem = new MenuItem("Add MySQL step");
			MenuItem addMongoItem = new MenuItem("Add Mongo step");
			MenuItem addSoapItem = new MenuItem("Add Soap step");
			MenuItem addRestItem = new MenuItem("Add Rest step");
			MenuItem removeItem = new MenuItem("Remove Test Case");
			contextMenu.getItems().addAll(addMysqlItem, addMongoItem, addSoapItem, addRestItem, removeItem);

			removeItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					TreeView<Object> treeView = getTreeView();
					TreeItem<Object> selectedItem = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
					if (selectedItem == null)
						return;

					TestProjectService testProjectService = new TestProjectService();
					testProjectService.removeTestCase(testCaseId);
					selectedItem.getParent().getChildren().remove(selectedItem);

					contentArea.setCenter(null);
				}
			});

			addMysqlItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					TreeView<Object> treeView = getTreeView();
					TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();

					if (item == null) {
						return;
					}

					MySQLStep mySQLStep = new MySQLStep();
					mySQLStep.setName("New MySQL Step");
					TestProjectService testProjectService = new TestProjectService();
					testProjectService.addStepForTestCase(mySQLStep, testCaseId);

					Node icon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.MYSQL_STEP_ICON.toString())));
					TreeItem<Object> stepNode = new TreeItem<>(mySQLStep, icon);
					item.getChildren().add(stepNode);
					treeView.getSelectionModel().select(stepNode);
					selectStep(mySQLStep.getID());
				}
			});

			addMongoItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					TreeView<Object> treeView = getTreeView();
					TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
					if (item == null)
						return;

					MongoStep mongoStep = new MongoStep();
					mongoStep.setName("New Mongo Step");
					TestProjectService testProjectService = new TestProjectService();
					testProjectService.addStepForTestCase(mongoStep, testCaseId);

					Node icon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.MONGO_STEP_ICON.toString())));
					TreeItem<Object> stepNode = new TreeItem<>(mongoStep, icon);
					item.getChildren().add(stepNode);
					treeView.getSelectionModel().select(stepNode);
					selectStep(mongoStep.getID());

				}
			});
			addSoapItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					TreeView<Object> treeView = getTreeView();
					TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
					if (item == null)
						return;

					SoapStep soapStep = new SoapStep();
					soapStep.setName("New Soap Step");
					TestProjectService testProjectService = new TestProjectService();
					testProjectService.addStepForTestCase(soapStep, testCaseId);

					Node icon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.SOAP_STEP_ICON.toString())));
					TreeItem<Object> stepNode = new TreeItem<>(soapStep, icon);
					item.getChildren().add(stepNode);
					treeView.getSelectionModel().select(stepNode);
					selectStep(soapStep.getID());
				}
			});

			addRestItem.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					TreeView<Object> treeView = getTreeView();
					TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
					if (item == null) {
						return;
					}

					RestStep restStep = new RestStep();
					restStep.setName("New Rest Step");
					TestProjectService testProjectService = new TestProjectService();
					testProjectService.addStepForTestCase(restStep, testCaseId);

					Node icon = new ImageView(new Image(getClass().getResourceAsStream(MainConstants.REST_STEP_ICON.toString())));
					TreeItem<Object> stepNode = new TreeItem<>(restStep, icon);
					item.getChildren().add(stepNode);
					treeView.getSelectionModel().select(stepNode);
					selectStep(restStep.getID());
				}
			});

			return contextMenu;
		}
	}
}