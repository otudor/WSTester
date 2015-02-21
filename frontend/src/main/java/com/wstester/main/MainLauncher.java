package com.wstester.main;

import com.wstester.RightClickMenu.RadialGlobalMenu;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainLauncher extends Application {
	
	public static Stage stage; 
    public static String screen1ID = "screen1";
    public static String screen1File = "/fxml/main/screen1.fxml";
    public static String screen2ID = "maineaa";
    public static String screen2File = "/fxml/main/WsTesterMain.fxml";
    public static String screen3ID = "loadingScreen";
    public static String screen3File = "/fxml/main/La.fxml";
    public static String screen4ID = "rigthClickMenu";
    public static String screen4File = "/fxml/main/RightClickMenu.fxml";
    public RadialGlobalMenu radialMenu;
    
    @Override
    public void start(Stage primaryStage) {
        
    	MainLauncher.stage=primaryStage;
    
        ScreensController mainContainer = new ScreensController();
        mainContainer.loadScreen(MainLauncher.screen1ID, MainLauncher.screen1File);
        mainContainer.loadScreen(MainLauncher.screen2ID, MainLauncher.screen2File);
        mainContainer.loadScreen(MainLauncher.screen3ID, MainLauncher.screen3File);
        mainContainer.loadScreen(MainLauncher.screen4ID, MainLauncher.screen4File);
        
        
        
        mainContainer.setScreen(MainLauncher.screen3ID);
//        mainContainer.unloadScreen(ScreensFramework.screen3ID);
       
        
        
        mainContainer.setScreen(MainLauncher.screen1ID);
        

       
        AnchorPane ancor = new AnchorPane();
        
        ancor.setMaxHeight(600);
        AnchorPane.setTopAnchor(mainContainer, (double) 0);
        AnchorPane.setRightAnchor(mainContainer, (double) 0);
        AnchorPane.setBottomAnchor(mainContainer, (double) 0);
        AnchorPane.setLeftAnchor(mainContainer, (double) 0);
        ancor.getChildren().addAll(mainContainer);
        Scene scene = new Scene(ancor);
        
        primaryStage.setMinHeight(825);
        primaryStage.setMinWidth(825);
//        primaryStage.setFullScreen(true);
        
        
        if (screen2ID.contains("maineaa")){
        	scene.getStylesheets().add(getClass().getResource("/styles/application.css").toExternalForm()); 
        }
       
        
        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
        if (mainContainer.getScreen(screen2ID)!=null) {
        	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    			public void handle(WindowEvent event) {
    				
    	    		ICamelContextManager manager = null;
					try {
						manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
					} catch (Exception e) {
						// TODO Make an exception screen to the user when he is notified that we can't close the camel context
						e.printStackTrace();
					}
					
    	    		manager.closeCamelContext();
    	    		System.exit(0); //NOPMD
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
