package com.wstester.main;

import com.wstester.elements.Dialog;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.util.MainConstants;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MainLauncher extends Application {
	
	public static Stage stage; 
    
    @Override
    public void start(Stage primaryStage) {
        
    	MainLauncher.stage=primaryStage;
    
        ScreensController mainContainer = new ScreensController();
        // loads all the screens that are used in the application
        mainContainer.loadScreen(MainConstants.HOME_PAGE_FXML, MainConstants.HOME_PAGE_FXML.toString());
        mainContainer.loadScreen(MainConstants.DESKTOP_FXML, MainConstants.DESKTOP_FXML.toString());
        mainContainer.loadScreen(MainConstants.WELCOME_FXML, MainConstants.WELCOME_FXML.toString());
        
        // sets the Welcome screen
        mainContainer.setScreen(MainConstants.WELCOME_FXML);
        
        // sets the Homepage screen
        mainContainer.setScreen(MainConstants.HOME_PAGE_FXML);
        
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
               
        primaryStage.setScene(scene);

        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    		public void handle(WindowEvent event) {
    				
    	   		ICamelContextManager manager = null;
				try {
					manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
				} catch (Exception e) {
					e.printStackTrace();
					Dialog.errorDialog("The application couldn't be closed. Please try again!", MainLauncher.stage);
				}
					
   	    		manager.closeCamelContext();
   	    		System.exit(0); //NOPMD
   			}
   		});  

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
