package com.wstester.plot2d;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DbController implements Initializable {

	@FXML
	private ImageView Db;
    private MySql input;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        this.print();
    }


	private void print() {
		Db.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Pane popContainer = new Pane();
				popContainer.setPrefSize(380, 300);
				
				Label port = new Label();
				port.setText("Port: ");
				port.setLayoutX(10);
				port.setLayoutY(10);
				TextField portField = new TextField();
				portField.setLayoutX(110);
				portField.setLayoutY(10);
				portField.setPrefHeight(5);
				portField.setPrefWidth(250);
				
				Label dbname = new Label();
				dbname.setText("DB Name: ");
				dbname.setLayoutX(10);
				dbname.setLayoutY(60);
				TextField dbNameField = new TextField();
				dbNameField.setLayoutX(110);
				dbNameField.setLayoutY(60);
				dbNameField.setPrefSize(250, 5);
				
				Label username = new Label();
				username.setText("Username: ");
				username.setLayoutX(10);
				username.setLayoutY(110);
				TextField dbUsernameField = new TextField();
				dbUsernameField.setLayoutX(110);
				dbUsernameField.setLayoutY(110);
				dbUsernameField.setPrefSize(250, 5);
				
				Label password = new Label();
				password.setText("Password: ");
				password.setLayoutX(10);
				password.setLayoutY(160);
				PasswordField dbPassField = new PasswordField();
				dbPassField.setLayoutX(110);
				dbPassField.setLayoutY(160);
				dbPassField.setPrefSize(250, 5);
				
				Button save = new Button();
				save.setText("Edit");
				save.setLayoutX(300);
				save.setLayoutY(240);
				
				
				
				popContainer.getChildren().addAll(port, portField, dbname, dbNameField, dbUsernameField,  username,  password, dbPassField, save);
				PopOver pop = new PopOver(popContainer);
				pop.setArrowLocation(PopOver.ArrowLocation.LEFT_BOTTOM);
		        pop.setHideOnEscape(true);
		        pop.setId("Db settings");
		        pop.setAutoFix(true);
//		        pop.setHeight(200);
//		        pop.setWidth(200);
		        pop.show(Db);
			}
		});
		
	}
    
    

}
