package com.wstester.variables;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.util.Callback;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.beans.binding.Bindings;

import com.wstester.log.CustomLogger;
import com.wstester.model.TestProject;
import com.wstester.model.Variable;
import com.wstester.model.VariableType;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class VariablesController {

	CustomLogger log = new CustomLogger(VariablesController.class);
	@FXML
	private TableView<Variable> variablesTable;
	@FXML
	private TableColumn<Variable, String> name;
	@FXML
	private TableColumn<Variable, VariableType> type;
	@FXML
	private TableColumn<Variable, String> selector;
	@FXML
	private TableColumn<Variable, String> content;
	@FXML
	private TextField searchBox;
	@FXML
	private TextField addName;
	@FXML
	private ComboBox<VariableType> addType;
	@FXML
	private TextField addSelector;
	@FXML
	private TextField addContent;
	@FXML
	private Button addButton;
	
	private IProjectFinder projectFinder;
	
	@FXML
	public void initialize() throws Exception{
		
		projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		initializeTable();
		loadVariables("");
		initializeSearch();
		initializeAddVariable();
	}

	private void initializeTable() {
		name.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
		name.setCellFactory(TextFieldTableCell.forTableColumn());
		name.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Variable,String>>() {
					
					@Override
					public void handle(CellEditEvent<Variable, String> newName) {
						log.info(((Variable) newName.getTableView().getItems().get(newName.getTablePosition().getRow())).getId(), "Changed name from " + newName.getOldValue() + " to " + newName.getNewValue());
						((Variable) newName.getTableView().getItems().get(newName.getTablePosition().getRow())).setName(newName.getNewValue());
					}
				}
		);
		
		type.setCellValueFactory(new PropertyValueFactory<Variable, VariableType>("type"));
		type.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(VariableType.values())));
		type.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Variable,VariableType>>() {
					
					@Override
					public void handle(CellEditEvent<Variable, VariableType> newType) {
						log.info(((Variable) newType.getTableView().getItems().get(newType.getTablePosition().getRow())).getId(), "Changed type from " + newType.getOldValue() + " to " + newType.getNewValue());
						((Variable) newType.getTableView().getItems().get(newType.getTablePosition().getRow())).setType(newType.getNewValue());
					}
				}
		);
		
		selector.setCellValueFactory(new PropertyValueFactory<Variable, String>("selector"));
		selector.setCellFactory(TextFieldTableCell.forTableColumn());
		selector.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Variable,String>>() {
					
					@Override
					public void handle(CellEditEvent<Variable, String> newSelector) {
						log.info(((Variable) newSelector.getTableView().getItems().get(newSelector.getTablePosition().getRow())).getId(), "Changed selector from " + newSelector.getOldValue() + " to " + newSelector.getNewValue());
						((Variable) newSelector.getTableView().getItems().get(newSelector.getTablePosition().getRow())).setSelector(newSelector.getNewValue());
					}
				}
		);
		
		content.setCellValueFactory(new PropertyValueFactory<Variable, String>("content"));
		content.setCellFactory(TextFieldTableCell.forTableColumn());
		content.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Variable,String>>() {
					
					@Override
					public void handle(CellEditEvent<Variable, String> newContent) {
						log.info(((Variable) newContent.getTableView().getItems().get(newContent.getTablePosition().getRow())).getId(), "Changed content from " + newContent.getOldValue() + " to " + newContent.getNewValue());
						((Variable) newContent.getTableView().getItems().get(newContent.getTablePosition().getRow())).setContent(newContent.getNewValue());
					}
				}
		);
		
		// set the context menu for non empty rows
		variablesTable.setRowFactory(new Callback<TableView<Variable>, TableRow<Variable>>() {
			@Override
			public TableRow<Variable> call(TableView<Variable> arg0) {
				
				 final TableRow<Variable> row = new TableRow<>();
				 final ContextMenu contextMenu = new ContextMenu();
				 
				 final MenuItem removeMenuItem = new MenuItem("Remove");
				 removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					 @Override
					 public void handle(ActionEvent event) {
						 
						 log.info(row.getItem().getId(), "Removing variable from variable list: " + row.getItem());
						 TestProject testProject = projectFinder.getTestProject();
			        	 testProject.getVariableList().remove(row.getItem());
			        	 loadVariables(searchBox.getText());
					 }
				 });
				 contextMenu.getItems().add(removeMenuItem);
				 
				 // Set context menu on row, but use a binding to make it only show for non-empty rows:
				 row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu)null).otherwise(contextMenu));
				 return row ; 
			}
		});
	}

	private void loadVariables(String textToSearch) {

		TestProject testProject = projectFinder.getTestProject();
		
		List<Variable> variableList = testProject.getVariableList();
		if(variableList != null) {
			ObservableList<Variable> variableFilteredList = getVariableFilteredList(variableList, textToSearch);
			variablesTable.setItems(variableFilteredList);
			log.info("Filtered variable list by " + textToSearch + ": " + variableFilteredList);
		}
	}

	private ObservableList<Variable> getVariableFilteredList(List<Variable> variableList, String text) {
		
		ObservableList<Variable> variableFilteredList = FXCollections.observableArrayList();
		
		for(Variable variable : variableList) {
			if(text.equals("") || containsString(variable.getName(), text) || containsString(variable.getType(), text) 
					|| containsString(variable.getSelector(), text) || containsString(variable.getContent(), text)) {
				
				variableFilteredList.add(variable);
			}
		}
		return variableFilteredList;
	}
	
	private boolean containsString(Object object, String text) {
		
		String string = (object != null ? object.toString() : "");
		return string.toLowerCase().contains(text.toLowerCase());
	}
	
	private void initializeSearch() {
		
		searchBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode().equals(KeyCode.ENTER)) {
					log.info("Searching for: " + searchBox.getText());
					loadVariables(searchBox.getText());
				}
			}
		});
	}

	private void initializeAddVariable() {
		addType.setItems(FXCollections.observableArrayList(VariableType.values()));
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	Variable variable = new Variable();
            	variable.setName(addName.getText());
            	variable.setType(addType.getValue());
            	variable.setSelector(addSelector.getText());
            	variable.setContent(addContent.getText());
            	
            	log.info(variable.getId(), "Adding variable to variable list: " + variable);
        		TestProject testProject = projectFinder.getTestProject();
        		testProject.getVariableList().add(variable);
        		loadVariables(searchBox.getText());
				
				addName.clear();
				addType.getSelectionModel().clearSelection();
				addType.setValue(null);
				addSelector.clear();
				addContent.clear();
            }
        });
	}
}