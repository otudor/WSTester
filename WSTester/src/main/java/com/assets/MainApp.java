package com.assets;


import java.io.IOException;

import javax.naming.event.EventContext;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Assets");
		
		try {
			FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/com/assets/sampleModified.fxml"));
			
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			//System.err.println("Error loading " + MainApp.class.getResource("sample1.fxml"));
			e.printStackTrace();
			System.out.println("EROARE");
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
