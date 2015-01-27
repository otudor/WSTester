package com.wstester.variables;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
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

import com.wstester.log.CustomLogger;
import com.wstester.model.TestProject;
import com.wstester.model.Variable;
import com.wstester.model.VariableType;
import com.wstester.util.TestProjectService;

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
	
	@FXML
	public void initialize(){
		
		initializeTable();
		loadVariables("");
		initializeSearch();
		initializeAddButton();
	}

	private void initializeAddButton() {
		addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	Variable variable = new Variable();
            	variable.setName(addName.getText());
            	variable.setType(addType.getValue());
            	variable.setSelector(addSelector.getText());
            	variable.setContent(addContent.getText());
				variablesTable.getItems().add(variable);
            }
        });
	}

	private void initializeTable() {
		name.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));
		name.setCellFactory(TextFieldTableCell.forTableColumn());
		name.setOnEditCommit(
				new EventHandler<TableColumn.CellEditEvent<Variable,String>>() {
					
					@Override
					public void handle(CellEditEvent<Variable, String> newName) {
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
						((Variable) newContent.getTableView().getItems().get(newContent.getTablePosition().getRow())).setContent(newContent.getNewValue());
						
					}
				}
		);
	}

	private void loadVariables(String textToSearch) {

		TestProjectService testProjectService = new TestProjectService();
		TestProject testProject = testProjectService.getTestProject();
		
		List<Variable> variableList = testProject.getVariableList();
		if(variableList != null) {
			ObservableList<Variable> variableFilteredList = getVariableFilteredList(variableList, textToSearch);
			variablesTable.setItems(variableFilteredList);
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
					
					loadVariables(searchBox.getText());
				}
			}
		});
	}
}
