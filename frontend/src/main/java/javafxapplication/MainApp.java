package javafxapplication;


import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.MinimizeIcon;
import jfxtras.labs.scene.control.window.Window;
import jfxtras.scene.layout.VBox;


public class MainApp extends Application {
	private static int counter = 1;

	private void init(Stage primaryStage) {    	
		final Group root = new Group();
		root.autosize();

		Button button = new Button("Assets");
		Button button2 = new Button("                REST");          
		
		root.getChildren().add(button2);
		root.getChildren().add(button);        
	


		primaryStage.setResizable(false);
		primaryStage.setScene(new Scene(root, 600, 500));

		button.setOnAction(new EventHandler<ActionEvent>() {            
			@Override
			public void handle(ActionEvent arg0) {
				try {
					Parent balou = FXMLLoader.load(getClass().getResource("/view/Assets.fxml"));
					Window w = new Window("Assets");
					w.setLayoutX(10);
					w.setLayoutY(10);
					// define the initial window size
					w.setPrefSize(500, 400);
					// either to the left
					w.getLeftIcons().add(new CloseIcon(w));
					// .. or to the right
					w.getRightIcons().add(new MinimizeIcon(w));
					//        w.setContentPane(new VBox());
					// add some content
					//    w.getContentPane().getChildren().add(new Label("Content... \nof the window#"+counter++));
					// add the window to the canvas
					//   w.getContentPane().getChildren().add()
					w.getContentPane().getChildren().add(balou);

					root.getChildren().add(w);  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		button2.setOnAction(new EventHandler<ActionEvent>() {            
			@Override
			public void handle(ActionEvent arg0) {
				// create a window with title "My Window"
				try {
					Parent balou = FXMLLoader.load(getClass().getResource("/view/Rest.fxml"));
					Window w = new Window("REST");
					w.setLayoutX(10);
					w.setLayoutY(10);
					// define the initial window size
					w.setPrefSize(500, 400);
					// either to the left
					w.getLeftIcons().add(new CloseIcon(w));
					// .. or to the right
					w.getRightIcons().add(new MinimizeIcon(w));
					//        w.setContentPane(new VBox());
					// add some content
					//    w.getContentPane().getChildren().add(new Label("Content... \nof the window#"+counter++));
					// add the window to the canvas
					//   w.getContentPane().getChildren().add()
					w.getContentPane().getChildren().add(balou);

					root.getChildren().add(w);  
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


	}

	public double getSampleWidth() {return 600;}
	public double getSampleHeight() {return 500;}

	@Override
	public void start(Stage primaryStage) throws Exception {
		init(primaryStage);
		primaryStage.show();
		primaryStage.setFullScreen(true);


	}
	public static void main(String[] args) {launch(args);}
}