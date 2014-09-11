package com.wstester.assets;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class PathController {
	
	@FXML
	private Button butonPath; // "Browse"
	@FXML 
	private Button butonApply; // "OK"
	@FXML 
	private Button butonCancel; // "OK"
	@FXML 
	private Button butonClear; // "OK"
	@FXML
	private TextField textPath;
	@FXML
	private Stage stagePath = new Stage();
	
	private Process process;
	
	@FXML
	private void initialize() {
		butonPath.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("lalal");
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("EXE files (*.exe)", "*.exe");
				fileChooser.getExtensionFilters().add(extFilter);
				
				File file = fileChooser.showOpenDialog(stagePath);
				
				if (file != null ) {
					
						try {
							String path = file.getCanonicalPath();
							textPath.setText(path);
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}}
				
			//	textPath.setText();
							
				
			}
			}); 
		
		
		
		butonApply.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(textPath.getText());
				
				/*Properties prop = new Properties();
				prop.setProperty("carte", "Ion");
				
				File file = new File("system.properties");
				FileOutputStream file out new FileOutputStream(file);*/
				
				

				System.out.println(System.getProperty("editor.path"));
				
				
			}
			}); 
	
		

}
	
	
}
