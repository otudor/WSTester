package com.assets;


import java.io.IOException;

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
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/jfxTbl/sampleModified.fxml"));
			AnchorPane page = (AnchorPane) loader.load();
			Scene scene = new Scene(page);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("EROARE");
		}
	}
	
	public static void main(String[] args) {
		Application.launch(MainApp.class);
	}
	
}
