package com.wstester.variables;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import com.wstester.log.CustomLogger;
import com.wstester.model.Step;
import com.wstester.model.Variable;
import com.wstester.model.VariableType;
import com.wstester.util.TestProjectService;

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
		
		TestProjectService testProjectService = new TestProjectService();
		Step step = testProjectService.getStep(stepId);
		List<Variable> variableList = step.getVariableList();
		if(variableList != null) {
			ObservableList<Variable> observableVariableList = FXCollections.observableArrayList(variableList);
			variablesTable.setItems(observableVariableList);
			log.info("List of the variables assigned on the step: " + variableList);
		}
	}
}