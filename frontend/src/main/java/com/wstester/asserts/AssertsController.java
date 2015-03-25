package com.wstester.asserts;

import java.util.List;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;

import com.wstester.log.CustomLogger;
import com.wstester.model.Assert;
import com.wstester.model.AssertOperation;
import com.wstester.model.Response;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class AssertsController {

	CustomLogger log = new CustomLogger(AssertsController.class);
	@FXML
	private TableView<AssertModel> assertTable;
	@FXML
	private TableColumn<AssertModel, String> actual;
	@FXML
	private TableColumn<AssertModel, AssertOperation> operation;
	@FXML
	private TableColumn<AssertModel, String> expected;
	@FXML
	private TableColumn<AssertModel, String> status;
	@FXML
	private TableColumn<AssertModel, String> message;
	@FXML
	private TextField addActual;
	@FXML
	private ComboBox<AssertOperation> addOperation;
	@FXML
	private TextField addExpected;
	@FXML
	private Button addButton;
	
	private String stepId;
	private IProjectFinder projectFinder;
	
	@FXML
	public void initialize() throws Exception {
		
		projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		initializeTable();
		initializeAddAssert();
	}

	public void setAssert(String stepId) {
		
		assertTable.setItems(null);
		this.stepId = stepId;
		Step step = projectFinder.getStepById(stepId);
		List<Assert> assertList = step.getAssertList();
		
		if(assertList != null) {
			
			log.info(stepId, "Assert list for the step is: " + assertList);
			ObservableList<AssertModel> observableAssertList = FXCollections.observableArrayList();
			for(Assert asert : assertList) {
				AssertModel assertModel = new AssertModel();
				assertModel.setAsert(asert);
				observableAssertList.add(assertModel);
			}
			assertTable.setItems(observableAssertList);
		} else {
			assertTable.setItems(FXCollections.observableArrayList());
		}
	}
	
	public void setAssertResponse(Response response) {
		
		ObservableList<AssertModel> assertsInTable = assertTable.getItems();
		for(AssertModel assertModel : assertsInTable) {
			String assertId = assertModel.getAsert().getId();
			assertModel.setAssertResponse(response.getResponseForAssertId(assertId));
		}
	}
	
	private void initializeTable() {
		actual.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AssertModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<AssertModel, String> asert) {
				return new SimpleStringProperty(asert.getValue().getAsert().getActual());
			}
		});
		actual.setCellFactory(TextFieldTableCell.forTableColumn());
		actual.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<AssertModel, String>>() {
			
					@Override
					public void handle(CellEditEvent<AssertModel, String> newActual) {
						log.info(((AssertModel) newActual.getTableView().getItems().get(newActual.getTablePosition().getRow())).getAsert().getId(), "Changed actual from " + newActual.getOldValue() + " to " + newActual.getNewValue());
						((AssertModel) newActual.getTableView().getItems().get(newActual.getTablePosition().getRow())).getAsert().setActual(newActual.getNewValue());
					}
		});
		
		operation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AssertModel, AssertOperation>, ObservableValue<AssertOperation>>() {

			@Override
			public ObservableValue<AssertOperation> call(CellDataFeatures<AssertModel, AssertOperation> asert) {
				return new SimpleObjectProperty<AssertOperation>(asert.getValue().getAsert().getOperation());
			}
		});
		operation.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(AssertOperation.values())));
		operation.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<AssertModel,AssertOperation>>() {
					
					@Override
					public void handle(CellEditEvent<AssertModel, AssertOperation> newOperation) {
						log.info(((AssertModel) newOperation.getTableView().getItems().get(newOperation.getTablePosition().getRow())).getAsert().getId(), "Changed operation from " + newOperation.getOldValue() + " to " + newOperation.getNewValue());
						((AssertModel) newOperation.getTableView().getItems().get(newOperation.getTablePosition().getRow())).getAsert().setOperation(newOperation.getNewValue());
					}
				}
		);
		
		expected.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AssertModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<AssertModel, String> asert) {
				return new SimpleStringProperty(asert.getValue().getAsert().getExpected());
			}
		});
		expected.setCellFactory(TextFieldTableCell.forTableColumn());
		expected.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<AssertModel, String>>() {
			
					@Override
					public void handle(CellEditEvent<AssertModel, String> newExpected) {
						log.info(((AssertModel) newExpected.getTableView().getItems().get(newExpected.getTablePosition().getRow())).getAsert().getId(), "Changed expected from " + newExpected.getOldValue() + " to " + newExpected.getNewValue());
						((AssertModel) newExpected.getTableView().getItems().get(newExpected.getTablePosition().getRow())).getAsert().setExpected(newExpected.getNewValue());
					}
		});
		
		status.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AssertModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<AssertModel, String> asert) {
				if(asert.getValue().getAssertResponse() != null) {
					return new SimpleStringProperty(asert.getValue().getAssertResponse().getStatus().toString());
				} else {
					return null;
				}
			}
		});
		
		message.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AssertModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<AssertModel, String> asert) {
				if(asert.getValue().getAssertResponse() != null) {
					return new SimpleStringProperty(asert.getValue().getAssertResponse().getMessage());
				} else {
					return null;
				}
			}
		});
				
		// set the context menu to remove asserts on non empty rows
		assertTable.setRowFactory(new Callback<TableView<AssertModel>, TableRow<AssertModel>>() {
			@Override
			public TableRow<AssertModel> call(TableView<AssertModel> table) {
				
				 final TableRow<AssertModel> row = new TableRow<AssertModel>();
				 final ContextMenu contextMenu = new ContextMenu();
				 
				 final MenuItem removeMenuItem = new MenuItem("Remove");
				 removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					 @Override
					 public void handle(ActionEvent event) {
						 
						 projectFinder.removeAssertFromStep(stepId, row.getItem().getAsert());
						 assertTable.getItems().remove(row.getItem());
					 }
				 });
				 contextMenu.getItems().add(removeMenuItem);
				 
				 // Set context menu on row, but use a binding to make it only show for non-empty rows:
				 row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu)null).otherwise(contextMenu));
				 return row ; 
			}
		});
	}

	private void initializeAddAssert() {
		addOperation.setItems(FXCollections.observableArrayList(AssertOperation.values()));
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	AssertModel assertModel = new AssertModel();
            	Assert asert = new Assert();
            	asert.setActual(addActual.getText());
            	asert.setOperation(addOperation.getValue());
            	asert.setExpected(addExpected.getText());
				assertModel.setAsert(asert);
				
				projectFinder.addAssertForStep(stepId, asert);
				assertTable.getItems().add(assertModel);
				
				addActual.clear();
				addOperation.getSelectionModel().clearSelection();
				addExpected.clear();
            }
		});
	}
}