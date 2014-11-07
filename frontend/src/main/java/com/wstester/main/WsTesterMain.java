package com.wstester.main;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Set;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import com.wstester.util.UtilityTool;

/**
 * Thiss class will be deleted in the newr future(2014/09/19)
 * @author lvasile
 *
 */
@Deprecated
public class WsTesterMain extends Application {
	
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
        loadConfiguration();
	}

	public static void main(String[] args) {launch(args);}
	
	private void loadConfiguration() throws IOException {
		InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream ("/system.properties"));
        Properties p = new Properties();
        p.load(reader);
        
        Set<?> keys = p.keySet();
        for(Object key : keys) {
        	UtilityTool.addOrReplaceProperty(key.toString(), p.getProperty(key.toString()));
        }
	}
}

