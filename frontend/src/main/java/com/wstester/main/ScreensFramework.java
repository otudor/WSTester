package com.wstester.main;

import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ScreensFramework extends Application {
    
    public static String screen1ID = "screen1";
    public static String screen1File = "/fxml/main/screen1.fxml";
    public static String screen2ID = "maineaa";
    public static String screen2File = "/fxml/main/WsTesterMain.fxml";
    public static String screen3ID = "screen3";
    public static String screen3File = "/fxml/main/La.fxml";
    
    
    @Override
    public void start(Stage primaryStage) {
        
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(ScreensFramework.screen1ID, ScreensFramework.screen1File);
        mainContainer.loadScreen(ScreensFramework.screen2ID, ScreensFramework.screen2File);
        mainContainer.loadScreen(ScreensFramework.screen3ID, ScreensFramework.screen3File);
        
        
        
        mainContainer.setScreen1(ScreensFramework.screen3ID);
       // mainContainer.unloadScreen(ScreensFramework.screen3ID);
        
        mainContainer.setScreen(ScreensFramework.screen1ID);
        
        
        Group root = new Group();
        root.getChildren().addAll(mainContainer);
        
        //root.getChildrenUnmodifiable().get(0).getStyleClass().add("mainWindow");
        //root.getChildrenUnmodifiable().get(1).getStyleClass().add("bar");
        Scene scene = new Scene(root);
        if (screen2ID.contains("maineaa")){
        	
        
       scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm());
       //mainContainer.getChildrenUnmodifiable().get(0).getStyleClass().add("mainWindow");
      // mainContainer.getChildrenUnmodifiable().get(1).getStyleClass().add("bar");
        }
        
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
