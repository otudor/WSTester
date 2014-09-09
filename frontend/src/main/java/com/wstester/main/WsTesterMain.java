package com.wstester.main;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.wstester.util.MainWindowListener;

public class WsTesterMain extends Application {
	
	private static List<MainWindowListener> listeners;
	
	@FXML 
	MenuBar menu;
	public static Stage stage;
	Scene scene;
	@Override
	public void start(Stage stage) throws Exception {
		//stage = new Stage(StageStyle.UTILITY);
		WsTesterMain.stage=stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main/WsTesterMain.fxml"));
        Scene scene = new Scene(root);
        
        scene.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());
        //stage.initStyle(StageStyle.TRANSPARENT);
        
        stage.toBack();
        
        stage.setTitle("WsTester");
        root.getChildrenUnmodifiable().get(0).getStyleClass().add("mainWindow");
        root.getChildrenUnmodifiable().get(1).getStyleClass().add("bar");
        stage.setScene(scene);
        //stage.setFullScreen(true);
        stage.show();
        listeners  = new ArrayList<>();
        
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				for(MainWindowListener listener : listeners) {
					listener.shutDown();
				}
			}
		});
	}

	public static void main(String[] args) {launch(args);}
	
	public static void registerListener(MainWindowListener listener) {
		if(listeners != null) {
			listeners.add(listener);
		}
	}

}

