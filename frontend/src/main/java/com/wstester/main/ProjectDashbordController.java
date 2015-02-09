package com.wstester.main;

import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.wstester.elements.Progress;
import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.IProjectFinder;
import com.wstester.services.definition.ITestProjectActions;
import com.wstester.util.MainConstants;
import com.wstester.util.UtilityTool;

/**
 * 
 * @author lvasile
 * TODO: add java doc   && rename class
 */
public class ProjectDashbordController implements Initializable, ControlledScreen {
	
	ITestProjectActions actions;
    ScreensController myController;
    public TestProject testproject;
    public static boolean loadState = false;
    
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

		newButton = (VBox) CreateButton("/images/button.png");
		newButton.setLayoutX(200);
		newButton.setLayoutY(400);

		newButtonLed = (VBox) CreateButton("/images/buttonn.png");
		newButtonLed.setLayoutX(200);
		newButtonLed.setLayoutY(400);
		
		loadButton = (VBox) CreateButton("/images/button_ini_load.png");
		loadButton.setLayoutX(600);
		loadButton.setLayoutY(400);
		
		loadButtonLed = (VBox) CreateButton("/images/button_hover_load.png");
		loadButtonLed.setLayoutX(600);
		loadButtonLed.setLayoutY(400);
		
		
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

				Progress p = new Progress();
				Stage stage1 = new Stage();

				@Override
				public void handle(MouseEvent event) {
					
			    	TestProject t = new TestProject();
			    	UtilityTool.addEntity(MainConstants.TEST_PROJECT, t);
			    	
			    	ICamelContextManager manager = null;
					try {
						manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
						IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
						projectFinder.setProject(t);
					} catch (Exception e) {
						// TODO Make an exception window that informs the user we could not open a new project
						// he should retry again the same operation
						e.printStackTrace();
					}
			    	try {
						p.start(stage1, manager);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
					myController.setScreen(MainLauncher.screen2ID);

				}
			});
//	 }
	}
	
	 
	 
	 private void loadExistingProject() {
		 loadButtonLed.setOnMouseClicked(new EventHandler<MouseEvent>() {
				
				Progress p = new Progress();
				Stage stage1 = new Stage();
				
				@Override
				public void handle(MouseEvent event) {
			    	 try {
			    		if (loadState==false){
			    		loadState= true;	
			    		FileChooser fileChooser = new FileChooser();
			    		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("STEP project files (*.step)", "*.step");
			    		fileChooser.getExtensionFilters().add(extFilter);
			    		File file = fileChooser.showOpenDialog(Load);
			    		
			    		if(file != null){
			    			
			    			actions = ServiceLocator.getInstance().lookup(ITestProjectActions.class);
			    			testproject = actions.open(file.getCanonicalPath());
			    			UtilityTool.addEntity(MainConstants.TEST_PROJECT, testproject);
			    			
				    		ICamelContextManager manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
							IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
							projectFinder.setProject(testproject);
							
					    	try {
								p.start(stage1, manager);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							myController.setScreen(MainLauncher.screen2ID);
							loadState=false;
			    		}
			    		else
			    		{
			    			loadState = false;
			    		}
			    		
			    		}		    					    		
					} catch (Exception e) {
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
