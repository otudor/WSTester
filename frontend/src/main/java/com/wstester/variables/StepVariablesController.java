package com.wstester.variables;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.wstester.elements.Dialog;
import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.model.VariableType;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IProjectFinder;

public class StepVariablesController implements Initializable{

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
	
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		
		name.setCellValueFactory(new PropertyValueFactory<Variable, String>("name"));		
		type.setCellValueFactory(new PropertyValueFactory<Variable, VariableType>("type"));
		selector.setCellValueFactory(new PropertyValueFactory<Variable, String>("selector"));				
		content.setCellValueFactory(new PropertyValueFactory<Variable, String>("content"));
	}

	public void setVariables(String stepId) {

		variablesTable.setItems(null);
		IProjectFinder projectFinder = null;
		try {
			projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
		} catch (Exception e) {
			e.printStackTrace();
			Dialog.errorDialog("Could not find the environmentList. Please try again!", null);
		}
		Step step = projectFinder.getStepById(stepId);
		List<String> stepVariableListId = step.getVariableList();
		
		if(stepVariableListId != null) {
			
			List<Variable> variableList = projectFinder.getTestProject().getVariableList();
			List<Variable> stepVariableList = variableList.parallelStream().filter(variable -> stepVariableListId.contains(variable.getId())).collect(Collectors.toList());
			if(stepVariableList != null) {
				ObservableList<Variable> observableVariableList = FXCollections.observableArrayList(stepVariableList);
				variablesTable.setItems(observableVariableList);
				log.info("List of the variables assigned on the step: " + variableList);
			}
		}
	}
}