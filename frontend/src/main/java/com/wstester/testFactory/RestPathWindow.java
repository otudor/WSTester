package com.wstester.testFactory;

import org.controlsfx.dialog.Dialogs;

import com.wstester.assets.EditController;
import com.wstester.testFactory.TableQuerry;
import com.wstester.testFactory.RestStepController;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RestPathWindow {

	@FXML 
	private TextField txtName;
	@FXML 
	private TextField txtValue;
	@FXML
	private Button save;
	@FXML
	private Button cancel;
		
public String valueName, valueContent;
	
	@FXML
	public void initialize(){
		//listener on textfield with name
		txtName.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String txtName) {
		    	valueName = txtName;
		    }
		});
		
		//listener on textfield with value
		txtValue.textProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable,
		            String oldValue, String txtValue) {
		    	valueContent = txtValue;
		    }
		});
		
		//event on Ok button from addVariables fxml
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
					if((valueContent != null && valueName != null) && (!valueContent.trim().isEmpty() && !valueName.trim().isEmpty())){
						RestStepController.stagePath.close();
						RestStepController.tablePathVarData.add(
								new TablePath(valueName, valueContent));
						RestStepController.populatePathTableStatic();
					}else{
						try {
							Dialogs.create()
							.lightweight()
							.owner(null)
							.title("Error")
							.message(
									"Please insert data in order to be added in the table!")
									.showError();
						}catch (Exception e) {
							e.printStackTrace();
						}
					}
			}
		});
		cancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				RestStepController.stagePath.close();
			}
		});
			}
	}
