package testing;

import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class Converter extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            final PipedOutputStream transformOutput = new PipedOutputStream();
            final PipedInputStream fxmlInputStream = new PipedInputStream(transformOutput);

            Thread transformThread = new Thread( () -> {
                try {
                    StreamSource xsltSource = new StreamSource(getClass().getResourceAsStream("xml2fxml.xsl"));
                   
                    Transformer transformer = TransformerFactory.newInstance().newTransformer(xsltSource);
                    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                    StreamSource xmlSource = new StreamSource(getClass().getResourceAsStream("questionnaire.xml"));
                    StreamResult transformerResult = new StreamResult(transformOutput);
                    transformer.transform(xmlSource, transformerResult);
                    transformOutput.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            transformThread.start();

            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(fxmlInputStream);
            Scene scene = new Scene(root, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}