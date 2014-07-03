/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafxapplication.MainApp;

/**
 * FXML Controller class
 *
 * @author atrandafir
 */
public class AssetsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
     public void createView() throws IOException {
            System.out.println("Creating view for assets");
                    
            Parent root = FXMLLoader.load(MainApp.class.getResource("/view/Assets.fxml"));
           
            Stage stage = new Stage();

            Scene scene = new Scene(root);

            


            stage.setScene(scene);
            
            stage.show();
            /*
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Wizard create new XML ");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(MainApp.stage);

            Scene scene = new Scene(page);
            dialogStage.setScene(scene);
           
            dialogStage.showAndWait();
            */
      
     }
    
}
