/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author atrandafir
 */
public class MainWindowController implements Initializable {
    
    private EventHandlerDemoController assetsC;
    private RestController restC;


    @FXML
    private Label label;
    @FXML
    private Button button1;
    @FXML
    private Button button2;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        label.setText("Button 1 clicked!");
        assetsC = new EventHandlerDemoController();
       // assetsC.createView();
    }
    
    @FXML
    private void secondButtonAction(ActionEvent event) {
        label.setText("Button 2 clicked!");
        restC = new RestController();
        restC.createView();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

}
