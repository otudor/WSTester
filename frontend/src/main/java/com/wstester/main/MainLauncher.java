package com.wstester.main;

import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainLauncher extends Application {
    
    public static String screen1ID = "screen1";
    public static String screen1File = "/fxml/main/screen1.fxml";
    public static String screen2ID = "maineaa";
    public static String screen2File = "/fxml/main/WsTesterMain.fxml";
    public static String screen3ID = "loadingScreen";
    public static String screen3File = "/fxml/main/La.fxml";
    
    
    @Override
    public void start(Stage primaryStage) {
        
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MainLauncher.screen1ID, MainLauncher.screen1File);
        mainContainer.loadScreen(MainLauncher.screen2ID, MainLauncher.screen2File);
        mainContainer.loadScreen(MainLauncher.screen3ID, MainLauncher.screen3File);
        
        
        
        mainContainer.setScreen(MainLauncher.screen3ID);
       // mainContainer.unloadScreen(ScreensFramework.screen3ID);
       
        
        
        mainContainer.setScreen(MainLauncher.screen1ID);
        
        
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
        if(mainContainer.getScreen(screen2ID)!=null){
        	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    			public void handle(WindowEvent we) {
    				
    				ServiceLocator serviceLocator = ServiceLocator.getInstance();
    	    		ICamelContextManager manager = serviceLocator.lookup(ICamelContextManager.class);
    	    		manager.closeCamelContext();
    	    		
    			}
    		});  
        	
        }
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
