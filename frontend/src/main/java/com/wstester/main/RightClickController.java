package com.wstester.main;

import java.net.URL;
import java.util.ResourceBundle;

import com.wstester.RightClickMenu.DemoUtil;
import com.wstester.RightClickMenu.RadialGlobalMenu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * 
 * @author lvasile
 * TODO: add java doc && rename class
 */
public class RightClickController implements Initializable, ControlledScreen {
	
	private Stage stageSoap = new Stage();
    ScreensController myController;
    private Group container;
    private RadialGlobalMenu radialMenu;
    private boolean visible = false;
	private Scene scene;
	@FXML
	private AnchorPane ancor;
	@FXML
	private Button buton;
	
	
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    	
    }
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

     @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen(MainLauncher.screen2ID);
    
        
       
     radialMenu = new RadialGlobalMenu();
// 	System.out.println(getClass().getClassLoader().);
 	radialMenu.addMenuItem("C:/Users/gvasile/Documents/GitHub/WSTester/frontend/src/main/resources/images/asset2.png", null);
 	radialMenu.computeItemsStartAngle();
// 	radialMenu.addMenuItem("resources/icons/gemicon/PNG/64x64/row 1/6.png", null);
// 	radialMenu.computeItemsStartAngle();
// 	radialMenu.addMenuItem("resources/icons/gemicon/PNG/64x64/row 4/6.png", null);
// 	radialMenu.computeItemsStartAngle();
// 	radialMenu.addMenuItem("resources/icons/gemicon/PNG/64x64/row 4/3.png", null);
// 	radialMenu.computeItemsStartAngle();
// 	radialMenu.addMenuItem("resources/icons/gemicon/PNG/64x64/row 6/14.png", null);
// 	radialMenu.computeItemsStartAngle();
// 	radialMenu.addMenuItem("resources/icons/gemicon/PNG/64x64/row 7/7.png", null);
// 	radialMenu.computeItemsStartAngle();
 
 	radialMenu.translateXProperty().bind(scene.widthProperty().divide(2.0));
 	radialMenu.translateYProperty()
 		.bind(scene.heightProperty().divide(2.0));
 	radialMenu.widthProperty().bind(scene.widthProperty());
 	radialMenu.heightProperty().bind(scene.heightProperty());
 	radialMenu.setVisible(visible);

 	
// 	final RadialMenuItem item = RadialMenuItemBuilder.create().build();
// 	item.setTranslateX(400);
// 	item.setTranslateY(300);

// 	final DemoUtil demoUtil = new DemoUtil();
 	
// 	demoUtil.addAngleControl("StartAngle", radialMenu.computeAreaInScreen());
 	
 	
 	
// 	final DemoUtil demoUtil = new DemoUtil();
// 	final RadialGlobalMenu demoUtil = new RadialGlobalMenu();
// 	demoUtil.addAngleControl("dada", radialMenu.s);
// 	demoUtil.addGraphicControl("ada", rad);
// 	demoUtil.addGraphicControl("adada", radialMenu.);
// 	demoUtil.addRadiusControl("start Angle", radialMenu.);
// 	demoUtil.addRadiusControl("Inner Radius", radialMenu.innerRadiusProperty());
// 	demoUtil.addRadiusControl("Radius", radialMenu.radiusProperty());
// 	demoUtil.addRadiusControl("Offset", radialMenu.offsetProperty());
// 	demoUtil.addRadiusControl("startAngle", radialMenu.);
// 	demoUtil.addColorControl("Background", item.backgroundFillProperty());
// 	demoUtil.addColorControl("BackgroundMouseOn",
// 	final RadialMenuItem itema = RadialMenuItemBuilder.create().build();
// 	itema.setTranslateX(400);
// 	itema.setTranslateY(300);
// 		itema.backgroundMouseOnFillProperty();
// 	demoUtil.addColorControl("Stroke", itema.strokeFillProperty());
// 	demoUtil.addColorControl("StrokeMouseOn",
// 		itema.strokeMouseOnFillProperty());
// 	demoUtil.addBooleanControl("BackgroundVisible",
// 		itema.backgroundVisibleProperty());
// 	demoUtil.addBooleanControl("StrokeVisible",
// 		itema.strokeVisibleProperty());
// 	demoUtil.addGraphicControl("Graphic",
// 		itema.graphicProperty());

 	final ToggleButton showHideButton = new ToggleButton("Toggle display");
 	showHideButton.setOnAction(new EventHandler<ActionEvent>() {

 	    @Override
 	    public void handle(final ActionEvent event) {
 		if (visible) {
 		    setRadialMenuVisible(false);
 		} else {
 		    setRadialMenuVisible(true);
 		}
 		visible = !visible;
 	    }
 	});
 	showHideButton.setTranslateX(20);
 	showHideButton.setTranslateY(20);

 	final ToggleButton exitButton = new ToggleButton("Exit");
 	exitButton.setOnAction(new EventHandler<ActionEvent>() {

 	    @Override
 	    public void handle(final ActionEvent event) {
 		System.exit(0);
 	    }
 	});
 	exitButton.setTranslateX(50);
 	exitButton.setTranslateY(20);
 	final HBox box = new HBox();
 	box.getChildren().addAll(showHideButton, exitButton);
 	DemoUtil demoUtil = new DemoUtil();
 	demoUtil.setTranslateX(100);
 	demoUtil.setTranslateY(300);
 	container.getChildren().addAll(radialMenu, demoUtil, box);
 	ancor.getChildren().add(exitButton);
 	

     }

     private void setRadialMenuVisible(final boolean visible) {
 	radialMenu.transitionVisible(visible);
     }

	
}