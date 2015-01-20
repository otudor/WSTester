package com.wstester.variables;

import java.util.List;
import java.util.function.Predicate;

import com.wstester.log.CustomLogger;
import com.wstester.model.TestProject;
import com.wstester.model.Variable;
import com.wstester.util.TestProjectService;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class VariablesController {
	
	CustomLogger log = new CustomLogger(VariablesController.class);
	@FXML
	private GridPane variablesGrid;
	@FXML
	private TextField searchBox;
	
	@FXML
	public void initialize(){
		
		loadVariables("");
		initializeGrid();
		initializeSearch();
	}

	// loads the Variables into the list
	// if text is "" then add all variables
	private void loadVariables(String text) {
		
		// remove all the children except the ones that are added from fxml
		// the ones from fxml have rowIndex == null
		variablesGrid.getChildren().removeIf(new Predicate<Node>() {
			@Override
			public boolean test(Node node) {
				if(GridPane.getRowIndex(node) == null) {
					return false;
				}
				return true;
			}
		});
		
		TestProjectService testProjectService = new TestProjectService();
		TestProject testProject = testProjectService.getTestProject();
		
		List<Variable> variableList = testProject.getVariableList();
		if(variableList != null) {
			for(Variable variable : variableList) {
				addGridRow(variable, testProject.getName(), text);
			}
		}
	}

	private void addGridRow(Variable variable, String hierarhy, String text) {
		
		if(text.equals("") || containsString(variable.getName(), text) || containsString(variable.getType(), text) 
				|| containsString(variable.getSelector(), text) || containsString(variable.getContent(), text) || containsString(hierarhy, text)){
			variablesGrid.addRow(variablesGrid.getChildren().size(), getLabel(variable.getName()), getLabel(variable.getType()), getLabel(variable.getSelector()), getLabel(variable.getContent()), getLabel(hierarhy));
		}
	}

	private boolean containsString(Object object, String text) {
		
		String string = (object != null ? object.toString() : "");
		return string.toLowerCase().contains(text.toLowerCase());
	}

	private Node getLabel(Object string) {
		
		Label label = new Label(string != null ? string.toString() : "");
		label.setFont(Font.font(16));
		
		return label;
	}
	
	private void initializeGrid() {
		
		for(Node children : variablesGrid.getChildren()) {
			children.setOnMouseEntered(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					Integer index = GridPane.getRowIndex(children);
					if(index != null) {
						for(Node rowChildren : variablesGrid.getChildren()) {
							if(GridPane.getRowIndex(rowChildren) != null && GridPane.getRowIndex(rowChildren).equals(index)) {
								rowChildren.setStyle("-fx-font-weight: bold;");
							}
						}
					}
				}
			});
			
			children.setOnMouseExited(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					Integer index = GridPane.getRowIndex(children);
					if(index != null) {
						for(Node rowChildren : variablesGrid.getChildren()) {
							if(GridPane.getRowIndex(rowChildren) != null && GridPane.getRowIndex(rowChildren).equals(index)) {
								rowChildren.setStyle("");
							}
						}
					}
				}
			});
		}
	}
	
	private void initializeSearch() {
		
		searchBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				
				if(event.getCode().equals(KeyCode.ENTER)) {
					
					loadVariables(searchBox.getText());
					initializeGrid();
				}
			}
		});
	}
}
