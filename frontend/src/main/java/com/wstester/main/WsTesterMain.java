package com.wstester.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
import com.wstester.util.Util;

public class WsTesterMain extends Application implements MainWindowListener {
	
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
        
        scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
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
        loadConfiguration();
	}

	public static void main(String[] args) {launch(args);}
	
	public static void registerListener(MainWindowListener listener) {
		if(listeners != null) {
			listeners.add(listener);
		}
	}
	
	private void loadConfiguration() throws IOException {
		InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream ("/system.properties"));
        Properties p = new Properties();
        p.load(reader);
        
        Set keys = p.keySet();
        for(Object key : keys) {
        	Util.addOrReplaceProperty(key.toString(), p.getProperty(key.toString()));
        }
	}

	@Override
	public void shutDown() {
		Properties props = Util.getRuntimeProperties();
		
		File f = new File("/system.properties");
		OutputStream out;
		try {
			out = new FileOutputStream(f);
			props.store(out, null);
		} catch (IOException ex) {
			//TODO: log exception
		}
	}
}

