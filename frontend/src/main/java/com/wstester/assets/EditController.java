package com.wstester.assets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextAreaBuilder;
import javafx.scene.layout.VBox;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;



public class EditController {
	XmlFormatter formatter = new XmlFormatter();
	@FXML
	private Button butonLoad;
	@FXML
	private Button butonPretty;
	@FXML
	private Button butonSave;
	@FXML
	private TextArea textarea;
	@FXML
	private Stage stageEditor;
	
	
	
	//@FXML
	//private void initialize() {
	
		/*but.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("apasat buton edit");
			}
			});*/
		@FXML
		private void initialize() {
		butonLoad.setOnAction(new EventHandler<ActionEvent>() {
		@Override	
         public void handle(ActionEvent arg0) {
             FileChooser fileChooser = new FileChooser();
             
             //Set extension filter
             FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.xml)", "*.xml");
             fileChooser.getExtensionFilters().add(extFilter);
              
             //Show save file dialog
           
             
			File file = fileChooser.showOpenDialog(stageEditor);
             if(file != null){
                 
                 textarea.setText(readFile(file));
                 
             }
		}

		});
		
		
		 butonPretty.setOnAction(new EventHandler<ActionEvent>() {

	            @Override
	            public void handle(ActionEvent event) {
	               
	                    if (textarea.getText().trim().length() != 0)
	                    textarea.setText(formatter.format(textarea.getText()));
	                    
	                }
	            
	        });
		
		butonSave.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
	          public void handle(ActionEvent event) {
	              FileChooser fileChooser = new FileChooser();
	  
	              //Set extension filter
	              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.xml)", "*.xml");
	              fileChooser.getExtensionFilters().add(extFilter);
	              
	              //Show save file dialog
	              File file = fileChooser.showSaveDialog(stageEditor);
	              
	              if(file != null){
	                  SaveFile(textarea.getText(), file);
	              }
	          }
	      });
		
     
		}  
     
		
		 private void SaveFile(String content, File file){
		        try {
		            FileWriter fileWriter = null;
		             
		            fileWriter = new FileWriter(file);
		            fileWriter.write(content);
		            fileWriter.close();
		        } catch (IOException ex) {
		            Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
		        }
		         
		    }
     
		
		
		
     private String readFile(File file){
         StringBuilder stringBuffer = new StringBuilder();
         BufferedReader bufferedReader = null;
          
         try {
  
             bufferedReader = new BufferedReader(new FileReader(file));
              
             String text;
             while ((text = bufferedReader.readLine()) != null) {
                 stringBuffer.append(text);
             }
  
         } catch (FileNotFoundException ex) {
             Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
         } catch (IOException ex) {
             Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
         } finally {
             try {
                 bufferedReader.close();
             } catch (IOException ex) {
                 Logger.getLogger(EditController.class.getName()).log(Level.SEVERE, null, ex);
             }
         } 
         
         
         return stringBuffer.toString();
         
     }

     public class XmlFormatter {
      
         
        
     /**
      *
      * @param unformattedXml - XML String
      * @return Properly formatted XML String
      */
     public String format(String unformattedXml) {
         try {
             Document document = parseXmlFile(unformattedXml);
  
             OutputFormat format = new OutputFormat(document);
             format.setLineWidth(65);
             format.setIndenting(true);
             format.setIndent(9);
             Writer out = new StringWriter();
             XMLSerializer serializer = new XMLSerializer(out, format);
             serializer.serialize(document);
  
             return out.toString();
         } catch (IOException e) {
             e.printStackTrace();
             return "";
         }
  
     }
  
     /**
      * This function converts String XML to Document object
      * @param in - XML String
      * @return Document object
      */
     private Document parseXmlFile(String in) {
         try {
             DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
             DocumentBuilder db = dbf.newDocumentBuilder();
             InputSource is = new InputSource(new StringReader(in));
             return db.parse(is);
         } catch (ParserConfigurationException e) {
             throw new RuntimeException(e);
         } catch (SAXException e) {
             throw new RuntimeException(e);
         } catch (IOException e) {
             e.printStackTrace();
         }
         return null;
     }
     /**
      * Takes an XML Document object and makes an XML String. 
      *
      * @param doc - The DOM document
      * @return the XML String
      */
     public String makeXMLString(Document doc) {
         String xmlString = "";
         if (doc != null) {
             try {
                 TransformerFactory transfac = TransformerFactory.newInstance();
                 Transformer trans = transfac.newTransformer();
                 trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
                 trans.setOutputProperty(OutputKeys.INDENT, "yes");
                 StringWriter sw = new StringWriter();
                 StreamResult result = new StreamResult(sw);
                 DOMSource source = new DOMSource(doc);
                 trans.transform(source, result);
                 xmlString = sw.toString();
             } catch (Exception e) {
                 e.printStackTrace();
             }
         }
         return xmlString;
		
     }}}
