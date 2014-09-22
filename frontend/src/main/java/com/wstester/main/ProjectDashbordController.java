package com.wstester.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.wstester.model.TestProject;
import com.wstester.services.impl.TestProjectActions;
import com.wstester.util.MainConstants;
import com.wstester.util.UtilityTool;

/**
 * 
 * @author lvasile
 * TODO: add java doc   && rename class
 */
public class ProjectDashbordController implements Initializable, ControlledScreen {
	
	TestProjectActions actions = new TestProjectActions();
    ScreensController myController;
    public TestProject testproject;
    
    @FXML
    private AnchorPane ancor = new AnchorPane();
    
    @FXML
    private VBox newButton = new VBox();
    private VBox loadButton = new VBox();
    private VBox newButtonLed= new VBox();
    private VBox loadButtonLed= new VBox();
    
    @FXML
    private Stage Load;
    
    
    
    public TestProject getTestproject() {
		return testproject;
	}

	public void setTestproject(TestProject testproject) {
		this.testproject = testproject;
	}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	this.createButtons();
    	this.loadNewProject();
    	this.loadExistingProject();
    	
    }
    
   

	private void createButtons() {
		newButton = (VBox) CreateButton("/images/but.png");
		newButton.setLayoutX(100);
		newButton.setLayoutY(100);
		
		newButtonLed = (VBox) CreateButton("/images/but1.png");
		newButtonLed.setLayoutX(100);
		newButtonLed.setLayoutY(100);
		
		loadButton = (VBox) CreateButton("/images/but.png");
		loadButton.setLayoutX(500);
		loadButton.setLayoutY(100);
		
		loadButtonLed = (VBox) CreateButton("/images/but1.png");
		loadButtonLed.setLayoutX(500);
		loadButtonLed.setLayoutY(100);
		
		
		ancor.getChildren().addAll(newButton,loadButton);
		
		newButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				ancor.getChildren().add(newButtonLed);
				ancor.getChildren().remove(newButton);
				
				
		
	}
		});
		newButtonLed.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				
				ancor.getChildren().remove(newButtonLed);
				ancor.getChildren().add(newButton);
				
		
	}
		});
		
		loadButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				ancor.getChildren().add(loadButtonLed);
				ancor.getChildren().remove(loadButton);
				
				
		
	}
		});
		loadButtonLed.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override public void handle(MouseEvent mouseEvent) {
				
				ancor.getChildren().remove(loadButtonLed);
				ancor.getChildren().add(loadButton);
				
		
	}
		});
    }
	
	
	 private void loadNewProject() {
			newButtonLed.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					myController.setScreen(MainLauncher.screen2ID);
			    	
			    	 try {
						testproject=actions.open("src/main/resources/testProject/Output.xml");
						UtilityTool.addEntity(MainConstants.TESTPROJECT, testproject); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			
		}
			});
			
	    }
	 
	 
	 private void loadExistingProject() {
			loadButtonLed.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					myController.setScreen(MainLauncher.screen2ID);
			    	
			    	 try {
			    		FileChooser fileChooser = new FileChooser();
			    		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("STEP project files (*.step)", "*.step");
			    		fileChooser.getExtensionFilters().add(extFilter);
			    		File file = fileChooser.showOpenDialog(Load);
			    		if(file != null){
			    			System.out.println(file.getCanonicalPath());
			    			testproject=actions.open(file.getCanonicalPath());
			    			UtilityTool.addEntity(MainConstants.TESTPROJECT, testproject);
			    		}
						//testproject=actions.open("src/main/resources/testProject/Output.xml");
						//UtilityTool.addEntity(MainConstants.TESTPROJECT, testproject); 
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			
		}
			});
			
	    }
    
    

	static Node CreateButton(String butonPath) {
		// TODO Auto-generated method stub
		VBox vbox2 = new VBox();
		ImageView imageComp = new ImageView(new Image(ProjectDashbordController.class.getResourceAsStream(butonPath)));
		vbox2.getChildren().addAll(imageComp);
		return vbox2;
	}

	public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

	
	
	
	
	
     
}
