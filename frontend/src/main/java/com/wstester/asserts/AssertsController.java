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
import javafx.util.Callback;

import com.wstester.model.Assert;
import com.wstester.model.AssertOperation;
import com.wstester.model.Step;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class AssertsController {

	@FXML
	private TableView<AssertModel> assertTable;
	@FXML
	private TableColumn<AssertModel, String> actual;
	@FXML
	private TableColumn<AssertModel, AssertOperation> operation;
	@FXML
	private TableColumn<AssertModel, String> expected;
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
	
	private void initializeTable() {
		actual.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AssertModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<AssertModel, String> asert) {
				return new SimpleStringProperty(asert.getValue().getAsert().getActual());
			}
		});
		operation.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AssertModel, AssertOperation>, ObservableValue<AssertOperation>>() {

			@Override
			public ObservableValue<AssertOperation> call(CellDataFeatures<AssertModel, AssertOperation> asert) {
				return new SimpleObjectProperty<AssertOperation>(asert.getValue().getAsert().getOperation());
			}
		});
		expected.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<AssertModel, String>, ObservableValue<String>>() {
			
			@Override
			public ObservableValue<String> call(CellDataFeatures<AssertModel, String> asert) {
				return new SimpleStringProperty(asert.getValue().getAsert().getExpected());
			}
		});
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