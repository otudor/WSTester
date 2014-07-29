package com.wstester;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;

public class WsTesterMain extends Application {
	@FXML 
	MenuBar menu;
	public static Stage stage;
	Scene scene;
	@Override
	public void start(Stage stage) throws Exception {
		//stage = new Stage(StageStyle.UTILITY);
		WsTesterMain.stage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/WsTesterMain.fxml"));
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());   	
        stage.setTitle("WsTester");
        root.getChildrenUnmodifiable().get(0).getStyleClass().add("mainWindow");
        root.getChildrenUnmodifiable().get(1).getStyleClass().add("bar");
        stage.setScene(scene);
        stage.show();
	}

	public static void main(String[] args) {launch(args);}

}

