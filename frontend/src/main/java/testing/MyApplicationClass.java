package testing;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.*;
import javafx.util.Duration;
 
/** Sample application to demonstrate programming an FXML interface. */ 
public class MyApplicationClass extends Application {
  @Override public void start(final Stage stage) throws Exception {
    // load the scene fxml UI.
    // grabs the UI scenegraph view from the loader.
    // grabs the UI controller for the view from the loader.
    final FXMLLoader loader = new FXMLLoader(getClass().getResource("myInterface.fxml"));
    final Parent root = (Parent) loader.load();
    final MyControllerClass controller = loader.<MyControllerClass>getController();
 
    // continuously refresh the TreeItems.
    // demonstrates using controller methods to manipulate the controlled UI.
    final Timeline timeline = new Timeline(
      new KeyFrame(
        Duration.seconds(3), 
        new TreeLoadingEventHandler(controller)
      )
    );
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
 
    // close the app if the user clicks on anywhere on the window.
    // just provides a simple way to kill the demo app.
    root.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
      public void handle(MouseEvent t) {
        stage.hide();
      }
    });
   
    // initialize the stage.
    stage.setScene(new Scene(root));
    stage.initStyle(StageStyle.TRANSPARENT);
    stage.getIcons().add(new Image(getClass().getResourceAsStream("myIcon.png")));
    stage.show();
  }
 
  /** small helper class for handling tree loading events. */
  private class TreeLoadingEventHandler implements EventHandler<ActionEvent> {
    private MyControllerClass controller;
    private int idx = 0;
    
    TreeLoadingEventHandler(MyControllerClass controller) {
      this.controller = controller;
    }
    
    public void handle(ActionEvent t) {
      controller.loadTreeItems("Loaded " + idx, "Loaded " + (idx + 1), "Loaded " + (idx + 2));
      idx += 3;
    }
  }
 
  // main method is only for legacy support - java 8 won't call it for a javafx application.
  public static void main(String[] args) { launch(args); }
}
