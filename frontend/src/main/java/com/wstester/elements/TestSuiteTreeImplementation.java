package com.wstester.elements;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.fxml.FXMLLoader;

import com.wstester.main.MainLauncher;
import com.wstester.model.Execution;
import com.wstester.model.ExecutionStatus;
import com.wstester.model.MongoStep;
import com.wstester.model.MySQLStep;
import com.wstester.model.RestStep;
import com.wstester.model.SoapStep;
import com.wstester.model.Step;
import com.wstester.model.TestCase;
import com.wstester.model.TestSuite;
import com.wstester.testFactory.TestMachineController;
import com.wstester.util.MainConstants;
import com.wstester.util.TestProjectService;

public class TestSuiteTreeImplementation extends TreeCell<Object> {

	public TestSuiteTreeImplementation() {

		// events when the user clicks on different levels of the tree view
		this.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				if ((event.getClickCount() == 1) && (event.getButton() == MouseButton.PRIMARY)) {
					if (getItem() != null) {

						TestMachineController testMachineController = getTestMachineController();
						
						if (getItem() instanceof TestSuite) {
							testMachineController.selectTestSuite(((TestSuite) getItem()).getID());
						} else if (getItem() instanceof TestCase) {
							testMachineController.selectTestCase(((TestCase) getItem()).getID());
						} else if (getItem() instanceof Step) {
							testMachineController.selectStep(((Step) getItem()).getID());
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

			if (getItem() instanceof Step) {
				setGraphic(createStepGraphic(item));
			}
			else {
				setText(getItem() == null ? "" : getItem().toString());
				setGraphic(getTreeItem().getGraphic());
			}
			
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

	public HBox createStepGraphic(Object item) {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(0, 0, 0, 0));
		hbox.setSpacing(5);

		Label lblNodeName = new Label(getItem() == null ? "" : getItem().toString());
		hbox.getChildren().add(getTreeItem().getGraphic());
		hbox.getChildren().addAll(lblNodeName);

		Execution execution = ((Step) getItem()).getLastExecution();
		if (execution != null) {
			ImageView pic = null;
			if (execution.getResponse().getStatus() == ExecutionStatus.PASSED)
				pic = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_step_passed.png")));

			hbox.getChildren().addAll(pic);
		}

		return hbox;
	}
	
	public ContextMenu createTestSuiteContextMenu(String testSuiteId) {
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem addTCMenu = new MenuItem("Add Test Case");
		MenuItem removeTS = new MenuItem("Remove Test Suite");
		contextMenu.getItems().addAll(addTCMenu, removeTS);

		TestMachineController testMachineController = getTestMachineController();
		addTCMenu.setOnAction(new EventHandler<ActionEvent>() {
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
				testMachineController.selectTestCase(testCase.getID());
			}
		});

		removeTS.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				TreeView<Object> treeView = testMachineController.getTreeView();
				TreeItem<Object> testSuiteItem = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
				if (testSuiteItem == null)
					return;

				TestProjectService testProjectService = new TestProjectService();
				testProjectService.removeTestSuite(testSuiteId);
				
				TreeItem<Object> rootItem = testSuiteItem.getParent();
				rootItem.setExpanded(false);
			}
		});

		return contextMenu;
	}

	public ContextMenu createTestStepContextMenu(String stepId) {
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem removeStep = new MenuItem("Remove");
		contextMenu.getItems().addAll(removeStep);

		removeStep.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeView<Object> treeView = getTreeView();
				TreeItem<Object> c = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
				int idx = treeView.getSelectionModel().getSelectedIndex();
				if (c == null) {
					return;
				}
				TestProjectService testProjectService = new TestProjectService();
				testProjectService.removeTestStep(stepId);
				c.getParent().getChildren().remove(c);
				treeView.getSelectionModel().select(idx > 0 ? idx - 1 : 0);
			}
		});
		return contextMenu;
	}

	public ContextMenu createTestCaseContextMenu(String testCaseId) {
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem addMysqlItem = new MenuItem("Add MySQL step" /* + ftp.getID() */);
		MenuItem addMongoItem = new MenuItem("Add Mongo step" /* + ftp.getID() */);
		MenuItem addSoapItem = new MenuItem("Add Soap step" /* + ftp.getID() */);
		MenuItem addRestItem = new MenuItem("Add Rest step" /* + ftp.getID() */);
		MenuItem removeItem = new MenuItem("Remove" /* + ftp.getID() */);
		contextMenu.getItems().addAll(addMysqlItem, addMongoItem, addSoapItem, addRestItem, removeItem);

		removeItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeView<Object> treeView = getTreeView();
				TreeItem<Object> c = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
				int idx = treeView.getSelectionModel().getSelectedIndex();
				if (c == null)
					return;

				TestProjectService testProjectService = new TestProjectService();
				testProjectService.removeTestCase(testCaseId);
				c.getParent().getChildren().remove(c);

				treeView.getSelectionModel().select(idx > 0 ? idx - 1 : 0);
			}
		});

		addMysqlItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TestMachineController testMachineController = getTestMachineController();
				TreeView<Object> treeView = getTreeView();
				TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();

				if (item == null) {
					return;
				}

				MySQLStep mySQLStep = new MySQLStep();
				mySQLStep.setName("New MySQL Step");
				TestProjectService testProjectService = new TestProjectService();
				testProjectService.addStepForTestCase(mySQLStep, testCaseId);

				Node icon = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
				TreeItem<Object> stepNode = new TreeItem<>(mySQLStep, icon);
				item.getChildren().add(stepNode);
				treeView.getSelectionModel().select(stepNode);
				testMachineController.selectStep(mySQLStep.getID());
			}
		});

		addMongoItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TestMachineController testMachineController = getTestMachineController();
				TreeView<Object> treeView = getTreeView();
				TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
				if (item == null)
					return;

				MongoStep mongoStep = new MongoStep();
				mongoStep.setName("New Mongo Step");
				TestProjectService testProjectService = new TestProjectService();
				testProjectService.addStepForTestCase(mongoStep, testCaseId);

				Node icon = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
				TreeItem<Object> stepNode = new TreeItem<>(mongoStep, icon);
				item.getChildren().add(stepNode);
				treeView.getSelectionModel().select(stepNode);
				testMachineController.selectStep(mongoStep.getID());

			}
		});
		addSoapItem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TestMachineController testMachineController = getTestMachineController();
				TreeView<Object> treeView = getTreeView();
				TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
				if (item == null)
					return;

				SoapStep soapStep = new SoapStep();
				soapStep.setName("New Soap Step");
				TestProjectService testProjectService = new TestProjectService();
				testProjectService.addStepForTestCase(soapStep, testCaseId);

				Node icon = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
				TreeItem<Object> stepNode = new TreeItem<>(soapStep, icon);
				item.getChildren().add(stepNode);
				treeView.getSelectionModel().select(stepNode);
				testMachineController.selectStep(soapStep.getID());
			}
		});

		addRestItem.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				TestMachineController testMachineController = getTestMachineController();
				TreeView<Object> treeView = getTreeView();
				TreeItem<Object> item = (TreeItem<Object>) treeView.getSelectionModel().getSelectedItem();
				if (item == null) {
					return;
				}

				RestStep restStep = new RestStep();
				restStep.setName("New Rest Step");
				TestProjectService testProjectService = new TestProjectService();
				testProjectService.addStepForTestCase(restStep, testCaseId);

				Node icon = new ImageView(new Image(getClass().getResourceAsStream("/images/treeIcon_TestStep.png")));
				TreeItem<Object> stepNode = new TreeItem<>(restStep, icon);
				item.getChildren().add(stepNode);
				treeView.getSelectionModel().select(stepNode);
				testMachineController.selectStep(restStep.getID());
			}
		});

		return contextMenu;
	}
	
	private TestMachineController getTestMachineController(){
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(MainConstants.TEST_MACHINE.toString()));
		try {
			loader.load();
		} catch (IOException e) {
			e.printStackTrace();
			Dialog.errorDialog("Can't open the test factory window. Please try again!", MainLauncher.stage);
		}
		return loader.<TestMachineController>getController();
	}
}