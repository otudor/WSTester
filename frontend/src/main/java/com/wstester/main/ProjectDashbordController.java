package com.wstester.main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.wstester.elements.Progress;
import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
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
		newButton = (VBox) CreateButton("/images/butNew.png");
		newButton.setLayoutX(100);
		newButton.setLayoutY(100);
		
		newButtonLed = (VBox) CreateButton("/images/butNewLed.png");
		newButtonLed.setLayoutX(100);
		newButtonLed.setLayoutY(100);
		
		loadButton = (VBox) CreateButton("/images/butLoad.png");
		loadButton.setLayoutX(500);
		loadButton.setLayoutY(100);
		
		loadButtonLed = (VBox) CreateButton("/images/butLoadLed.png");
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

				Progress p = new Progress();
				Stage stage1 = new Stage();
				@Override
				public void handle(MouseEvent event) {
					
			    	TestProject t = new TestProject();
			    	UtilityTool.addEntity(MainConstants.TEST_PROJECT, t);
			    	
			    	ICamelContextManager manager = null;
					try {
						manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
					} catch (Exception e) {
						// TODO Make an exception window that informs the user we could not open a new project
						// he should retry again the same operation
						e.printStackTrace();
					}
			    	try {
						p.start(stage1, manager );
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					myController.setScreen(MainLauncher.screen2ID);

				}
			});
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
			    			
				    		ICamelContextManager manager = null;

				    		manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
							
				    		try {
								p.start(stage1, manager );
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
