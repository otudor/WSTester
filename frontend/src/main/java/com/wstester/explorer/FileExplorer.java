package com.wstester.explorer;


	
	
	
	import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
	

	public class FileExplorer extends Application {

	    public static void main(String[] args) {
	        launch(args);
	    }

	    @Override
	    public void start(Stage stage) throws Exception {
	    /*//    final Model model = new Model();

	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(getClass().getResource("AssetExplorer.fxml"));
	        loader.setControllerFactory(new Callback<Class<?>, Object>() {
	            @Override
	            public Object call(Class<?> aClass) {
				//	return aClass;
	                return new AssetExplorerController();
	            }
	        });
	    //    AnchorPane root = (AnchorPane) loader.load();

	        primaryStage.setTitle("Hello World");
	  //      primaryStage.setScene(new Scene(root, 300, 275));
	        primaryStage.show();*/
	    	
	    	
	    	
	    	
	    
	    		    // load the scene fxml UI.
	    		    // grabs the UI scenegraph view from the loader.
	    		    // grabs the UI controller for the view from the loader.
	    		    final FXMLLoader loader = new FXMLLoader(getClass().getResource("2.fxml"));
	    		    final Parent root =  loader.load();
	    		    final AssetExplorerController controller = loader.<AssetExplorerController>getController();
	    		    
	    		    
	    		    // continuously refresh the TreeItems.
	    		    // demonstrates using controller methods to manipulate the controlled UI.
	    		
	    		   
	    		    // initialize the stage.
	    		    stage.setScene(new Scene(root));
	                controller.setStage(stage);
	           
	    		    
	    		    //stage.initStyle(StageStyle.TRANSPARENT);
	    		  //  stage.getIcons().add(new Image(getClass().getResourceAsStream("myIcon.png")));
	    		    stage.show();
	    		    
	    }
	}


