package com.wstester.variables;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javafx.beans.binding.Bindings;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.model.VariableType;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class StepVariablesController {

	CustomLogger log = new CustomLogger(StepVariablesController.class);
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
	private TextField addName;
	@FXML
	private ComboBox<VariableType> addType;
	@FXML
	private TextField addSelector;
	@FXML
	private TextField addContent;
	@FXML
	private Button addButton;
	
	private String stepId;
	private IProjectFinder projectFinder;
	private AutoCompletionBinding<String> nameBinding;
	@FXML
	public void initialize() throws Exception {
		
		projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		name.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));		
		type.setCellValueFactory(new PropertyValueFactory<Variable, VariableType>("type"));
		selector.setCellValueFactory(new PropertyValueFactory<Variable, String>("selector"));				
		content.setCellValueFactory(new PropertyValueFactory<Variable, String>("content"));
		
		// set the context menu to remove variables for non empty rows
		variablesTable.setRowFactory(new Callback<TableView<Variable>, TableRow<Variable>>() {
			@Override
			public TableRow<Variable> call(TableView<Variable> arg0) {
				
				 final TableRow<Variable> row = new TableRow<>();
				 final ContextMenu contextMenu = new ContextMenu();
				 
				 final MenuItem removeMenuItem = new MenuItem("Remove");
				 removeMenuItem.setOnAction(new EventHandler<ActionEvent>() {
					 @Override
					 public void handle(ActionEvent event) {
						 
						 projectFinder.removeVariableFromStep(stepId, row.getItem().getId());
						 variablesTable.getItems().remove(row.getItem());
					 }
				 });
				 contextMenu.getItems().add(removeMenuItem);
				 
				 // Set context menu on row, but use a binding to make it only show for non-empty rows:
				 row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu)null).otherwise(contextMenu));
				 return row ; 
			}
		});
	}

	// set the content of the view
	public void setVariables(String stepId) {

		variablesTable.setItems(null);
		this.stepId = stepId;
		Step step = projectFinder.getStepById(stepId);
		List<String> stepVariableListId = step.getVariableList();
		
		// add all variables from the step to the table
		if(stepVariableListId != null) {
			
			List<Variable> variableList = projectFinder.getTestProject().getVariableList();
			List<Variable> stepVariableList = variableList.parallelStream().filter(variable -> stepVariableListId.contains(variable.getId())).collect(Collectors.toList());
			if(stepVariableList != null) {
				ObservableList<Variable> observableVariableList = FXCollections.observableArrayList(stepVariableList);
				variablesTable.setItems(observableVariableList);
				log.info("List of the variables assigned on the step: " + variableList);
			}
		}
		
		// initialize the add Variable functionality
		initializeAddVariableForStep();
	}
	
	private void initializeAddVariableForStep() {
		
		//clear all the fields
		clearAddVariableFields();
		
		// bind the autocomplete text field so that it doesn't contain the variables already assigned on the step
		if(nameBinding != null) {
			nameBinding.dispose();
		}
		List<String> autocompletList = getAutocompleteList();
		nameBinding = TextFields.bindAutoCompletion(addName, autocompletList);
		
		// when the user selects a variable then autocomplete the rest of the fields describing the variable
		addName.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode().equals(KeyCode.ENTER)) {
					
					Variable variable = projectFinder.getVariableByName(addName.getText());
					if(variable != null) {
						addType.setValue(variable.getType());
						addSelector.setText(variable.getSelector());
						addContent.setText(variable.getContent());
					}
				}
			}
		});
		// add the variable to the step
		addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            	
            	Variable variable = projectFinder.getVariableByName(addName.getText());
            	if(variable != null) {
	        		projectFinder.addVariableForStep(stepId, variable.getId());
	        		setVariables(stepId);
            	}
            }
        });
	}

	private List<String> getAutocompleteList() {
		
		List<Variable> variableList = projectFinder.getTestProject().getVariableList();
		ObservableList<Variable> stepVariables = variablesTable.getItems();
		List<String> autocompleteList = new ArrayList<String>();
		
		variableList.forEach(variable -> {
			
			if(stepVariables == null) {
				autocompleteList.add(variable.getName());
			}
			else if(!stepVariables.contains(variable)) {
				autocompleteList.add(variable.getName());			
			}
		});
		
		return autocompleteList;
	}

	private void clearAddVariableFields() {
		
		addName.clear();
		addType.getSelectionModel().clearSelection();
		addType.setValue(null);
		addSelector.clear();
		addContent.clear();
	}
}