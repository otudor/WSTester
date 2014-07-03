package javafxapplication;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.labs.scene.control.window.CloseIcon;
import jfxtras.labs.scene.control.window.MinimizeIcon;
import jfxtras.labs.scene.control.window.Window;


public class MainApp extends Application {
private static int counter = 1;

private void init(Stage primaryStage) {
    final Group root = new Group();

    Button button = new Button("Add more windows");     

    root.getChildren().addAll(button);
    primaryStage.setResizable(false);
    primaryStage.setScene(new Scene(root, 600, 500));

    button.setOnAction(new EventHandler<ActionEvent>() {            
        @Override
        public void handle(ActionEvent arg0) {
            // create a window with title "My Window"
            Window w = new Window("My Window#"+counter);
            // set the window position to 10,10 (coordinates inside canvas)
            w.setLayoutX(10);
            w.setLayoutY(10);
            // define the initial window size
            w.setPrefSize(300, 200);
            // either to the left
            w.getLeftIcons().add(new CloseIcon(w));
            // .. or to the right
            w.getRightIcons().add(new MinimizeIcon(w));
       
            // add some content
            w.getContentPane().getChildren().add(new Label("Content... \nof the window#"+counter++));
            // add the window to the canvas
            root.getChildren().add(w);  
        }
    });
}

public double getSampleWidth() {return 600;}
public double getSampleHeight() {return 500;}

@Override
public void start(Stage primaryStage) throws Exception {
    init(primaryStage);
    primaryStage.show();


}
    public static void main(String[] args) {launch(args);}
}