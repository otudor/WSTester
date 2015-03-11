package com.wstester.elements;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.wstester.main.MainLauncher;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;


public class Progress  {

	final Float[] values = new Float[] {-1.0f};
	final Label [] labels = new Label[values.length];
	final ProgressIndicator[] pins = new ProgressIndicator[values.length];
	final HBox hbs [] = new HBox [values.length];
	ICamelContextManager camelContextManager;
	Boolean started = new Boolean(false);
	Boolean started1 = new Boolean(false);
	
	@FXML
	private AnchorPane pane;
	
	public void start(Stage stage , ICamelContextManager camelContextManager  )  {

		Task<Void> task1 = new Task<Void>() {
			@Override public Void call() {
				while(!started) {
				}
				return null;
			}
		};

		final ProgressIndicator updProg = new ProgressIndicator(0);
		
		updProg.progressProperty().bind(task1.progressProperty());
		updProg.setMaxSize(200, 200);
		
		StackPane layout = new StackPane();

		layout.getChildren().add(updProg);
		layout.setAlignment(Pos.CENTER);

		final Scene scene = new Scene(layout);
		
		stage.setScene(scene);
		stage.centerOnScreen();
		
		stage.setWidth(MainLauncher.stage.getWidth());
		stage.setHeight(MainLauncher.stage.getHeight());
		stage.toFront();
		scene.setFill(null);
		stage.setScene(scene);
		
		
		if(!started1){
		
			stage.initOwner(MainLauncher.stage);
			stage.initStyle(StageStyle.UNDECORATED);
			
			started1=true;
		
		}
		stage.show();
		Thread th = new Thread(task1);
		th.setDaemon(true);
		th.start();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					startContext( stage  );
				} catch (Exception e) {
					e.printStackTrace();
				}	
			}
		}).start();

	}
	public void startContext( Stage stage ) throws Exception{
		
		camelContextManager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
		camelContextManager.startCamelContext();
		started = true;
		
		Task<Void> task = new Task<Void>() {

			@Override public Void call(){

				stage.close();

				return null;
			}
		};
		Platform.setImplicitExit(false);
		Platform.runLater((task));
	}

}
