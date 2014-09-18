package com.wstester.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Start extends Application {

	 @Override
	    public void start(Stage stage) throws Exception {
		 Parent root = FXMLLoader.load(getClass().getResource("/fxml/main/screen1.fxml"));
	        Scene scene = new Scene(root);
	        
	      //  scene.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());
	        //stage.initStyle(StageStyle.TRANSPARENT);
	        
	        stage.toBack();
	        
	        stage.setTitle("WsTester");
	       // root.getChildrenUnmodifiable().get(0).getStyleClass().add("mainWindow");
	        //root.getChildrenUnmodifiable().get(1).getStyleClass().add("bar");
	        stage.setScene(scene);
	        //stage.setFullScreen(true);
	        stage.show();
	    }
	public static void main(String[] args) {launch(args);}
}
