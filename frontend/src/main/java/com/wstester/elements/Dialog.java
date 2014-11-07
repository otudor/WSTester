package com.wstester.elements;

import java.util.Optional;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.StageStyle;
import javafx.stage.Stage;
import jfxtras.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Platform;

public class Dialog {

	public static void errorDialog(String message, Stage owner){
		
		// Construct an OK button
		ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
		
		// Construct the alert with the necessary properties 
		Alert alert = new Alert(AlertType.ERROR, message, okButton);
		alert.initOwner(owner);
		alert.initStyle(StageStyle.TRANSPARENT);
		alert.getDialogPane().getParent().setStyle("-fx-border-style: solid; -fx-border-color: firebrick; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px");
		alert.show();
	}
	
	public static Pair twoTextBoxDialog(String firstBox, String secondBox, Stage owner){
		
		// Construct the dialog and the buttons
		javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<ButtonType>();
		ButtonType saveButtonType = new ButtonType("Save", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);
		
		// Make a grid pane with 2 Text Fields: Key and Value
		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));
		
		TextField key = new TextField();
		TextField value = new TextField();

		grid.add(new Label(firstBox), 0, 0);
		grid.add(key, 1, 0);
		grid.add(new Label(secondBox), 0, 1);
		grid.add(value, 1, 1);
		
		// set the properties to the dialog
		dialog.getDialogPane().setContent(grid);
		dialog.getDialogPane().getParent().setStyle("-fx-border-style: solid; -fx-border-color: green; -fx-border-width: 2px; -fx-border-radius: 10px; -fx-background-radius: 10px");
		dialog.initStyle(StageStyle.TRANSPARENT);
		dialog.initOwner(owner);
		
		// disable the Save button until the user inputs some valid data
		Node saveButton = dialog.getDialogPane().lookupButton(saveButtonType);
		// by default disabled
		saveButton.setDisable(true);
		// if the new value of the key field is not empty and value is not empty, activate the button
		key.textProperty().addListener((observable, oldValue, newValue) -> {
			saveButton.setDisable(newValue.trim().isEmpty() || value.getText().trim().isEmpty());
		});
		// if the new value of the value field is not empty and key is not empty, activate the button
		value.textProperty().addListener((observable, oldValue, newValue) -> {
			saveButton.setDisable(newValue.trim().isEmpty() || key.getText().trim().isEmpty());
		});
		
		// Request focus on the key field by default.
		Platform.runLater(() -> key.requestFocus());
		
		// wait until the user presses a button
		Optional<ButtonType> pressedButton = dialog.showAndWait();
		
		// if the button is Save
		if (pressedButton.get() == saveButtonType){
			return new Pair(key.getText(), value.getText());
		}
		else {
			return null;
		}
	}
}
