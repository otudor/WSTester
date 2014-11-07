package com.wstester.assets;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import com.wstester.model.Asset;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IAssetManager;

public class EditController {
	
	private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
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
	
	private Asset asset;
	
	public void setAsset(Asset asset){
		
		this.asset = asset;
	}
	
	public void updatePage(String fileName) {
	    //textarea.setText(text);
		IAssetManager assetManager = null;
		try {
			assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);
		} catch (Exception e) {
			// Inform the user that the content could not be loaded
			e.printStackTrace();
		}
		
		textarea.setText(assetManager.getAssetContent(fileName));
		//assetManager.close();
	    
	}
	
	
//	private ProcessBuilder processBuilder;
	
//	private Process process;

	//@FXML
	//private void initialize() {

	/*but.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("apasat buton edit");
			}
			});*/
	
	@FXML
	private void initialize() throws IOException {
		
		
		
		butonLoad.setOnAction(new EventHandler<ActionEvent>() {
			@Override	
			public void handle(ActionEvent arg0) {
				//handleLoad();
				incercare();
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
				try {
					saveFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
		
		//WsTesterMain.registerListener(this);
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

		 }
	}
	
	
	private void incercare(){
		//File file = new File ("C:\\Users\\gvasile\\Desktop\\mydocument.xml");
		InputStream input = getClass().getResourceAsStream("C:\\Users\\gvasile\\Desktop\\mydocument.xml");
		try {
		 BufferedReader reader=new BufferedReader(new InputStreamReader(input));
	    String line=null;
	    while((line=reader.readLine())!=null){
            System.out.println(line);
        }}
	    catch (Exception e) {
    // TODO Auto-generated catch block
    e.printStackTrace();}
		//InputStream in = FileLoadder.class.getResourceAsStream("<relative path from this class to the file to be read>");
		//System.out.println(file);
	}

	private void handleLoad() throws Exception {
//		String editorPath = System.getProperty("editor.path");
	//	String propr = Util.getPropertyValue(MainConstants.EDITORPATH.name());
//		processBuilder = new ProcessBuilder(editorPath);
//		process = processBuilder.start();
		
		//Runtime runtime = Runtime.getRuntime();
		//process = runtime.exec(editorPath + " " + "C:\\Users\\gvasile\\Desktop\\mydocument.xml");
		
		FileChooser fileChooser = new FileChooser();

		//Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		//Show save file dialog


		File file = fileChooser.showOpenDialog(stageEditor);
		if(file != null){

		IAssetManager assetManager = ServiceLocator.getInstance().lookup(IAssetManager.class);
		//TODO: get asset from the list in the EventHandlerDemoController
		//String content = assetManager.getAssetContent(asset);
		//textarea.setText(content);
		}
	}

	/*private void saveFile() {
		//TODO: move this code in AssetManager
		//TODO: disable save button
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
//				assetManager.saveAsset(asset);

				FileChooser fileChooser = new FileChooser();
				//Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.xml)", "*.xml");
				fileChooser.getExtensionFilters().add(extFilter);

				//Show save file dialog
				File file = fileChooser.showSaveDialog(stageEditor);

				if(file != null){
					FileWriter fileWriter = null;
					try {
						fileWriter = new FileWriter(file);
						fileWriter.write(textarea.getText());
						fileWriter.close();
					} catch (IOException e) {
						Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Exception occured while saving file: " + file.getName() + " due to following exception: " + e);
					}
				}
				
				
				return null;
			}
			
		};
		
		//ProgressBar bar = new ProgressBar();                            //progress bar ideea 
		//bar.progressProperty().bind(task.progressProperty());
		//new Thread(task).start();
		//TODO: butonSave enable
	}       */

//	public void shutDown() {
	//	if(process != null) {
	//		process.destroy();
	//	}
	//}
	
	
	private void saveFile() throws IOException {
		
		
		FileChooser fileChooser = new FileChooser();

		//Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		//Show save file dialog


		File file = fileChooser.showSaveDialog(stageEditor);
		if(file != null){
			FileWriter fileWriter = null;
			
				fileWriter = new FileWriter(file);
				fileWriter.write(textarea.getText());
				fileWriter.close();

		
		}
	}
}
