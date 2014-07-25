package com.assets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.commons.io.FileUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;


public class EventHandlerDemoController{
	
	private AssetModel assetModel;
	private AssetModel newValue, oldValue;
	
	@FXML
	private TableView<Table> tableView = new TableView<>();
	@FXML
	private TableColumn<Table, String> tableName;
	@FXML
	private TableColumn<Table, String> tableDate;
	@FXML
	private TableColumn<Table, String> tablePath;
	@FXML	
	private TableColumn<Table, String> tableSize;
	@FXML
	private Button addButton;
	@FXML
	private ListView<AssetModel> listView;
	
	private ObservableList<AssetModel> listViewData = FXCollections.observableArrayList();
	private ObservableList<Table> tableViewData = FXCollections.observableArrayList();

	public EventHandlerDemoController() {
		// Create some sample data for the ComboBox and ListView
		
		for(int i=0; i<TypeInterface.types.length; i++){
			assetModel = new AssetModel(TypeInterface.types[i]);
			System.out.println(TypeInterface.types[i]);
			listViewData.add(assetModel);
		}
		
	}
	
	public ObservableList<Table> getData() {
        return tableViewData;
    }
	
	
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	private void linkValues(AssetModel oldValue, AssetModel newValue){
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	@FXML
	private void initialize() {
		
		
		// Init ListView and listen for selection changes
		listView.setItems(listViewData);
		tableView.setItems(tableViewData);
		
		listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<AssetModel>() {

		    @Override
		    public void changed(ObservableValue<? extends AssetModel> observable, AssetModel oldValue, AssetModel newValue) {
		        // add data for xml files
		    	
		    	linkValues(oldValue, newValue);
		    	
		       if(newValue.toString() == TypeInterface.types[0]){
		    	   removeContent();
					
					getData().add(new Table("fisier1.xml", "21/11/2014", "C:\\Fisiere", "33KB"));
					getData().add(new Table("fisier2.xml", "21/04/2014", "C:\\FisiereXml", "24KB"));
					getData().add(new Table("fisier3.xml", "29/06/2014", "C:\\Fisieree", "19KB"));
					getData().add(new Table("fisier4.xml", "05/09/2014", "C:\\FisiereCD", "21KB"));
					
					setCellValueFactory();
					
				 // add data for xsd files
		       }else if(newValue.toString() == TypeInterface.types[1]){
		    	   
		    	    removeContent();
					getData().add(new Table("fisier1.xsd", "19/10/2014", "C:\\Fisiere", "21 KB"));
					getData().add(new Table("fisier2.xsd", "13/11/2013", "C:\\FisiereXsd", "34 KB"));
					getData().add(new Table("fisier3.xsd", "09/01/2014", "C:\\FisiereeMari", "45 KB"));
					getData().add(new Table("fisier4.xsd", "28/08/2013", "C:\\FisiereCD", "121 KB"));
					setCellValueFactory();
		       }else if(newValue.toString() == TypeInterface.types[2]){
		    	   
		    	    removeContent();
					getData().add(new Table("json1.json", "04/09/2014", "C:\\FisiereJson", "13 KB"));
					getData().add(new Table("fileJson.json", "02/02/2014", "C:\\Json", "92 KB"));
					getData().add(new Table("fisier3.json", "31/12/2013", "C:\\IncercariJson", "32 KB"));
					getData().add(new Table("fileTry.json", "21/08/2012", "C:\\FilesJson", "82 KB"));
					setCellValueFactory();
		       }else if(newValue.toString() == TypeInterface.types[3]){
		    	   
		    	    removeContent();
					getData().add(new Table("csv_1.csv", "11/09/2014", "C:\\CSVs", "15 KB"));
					getData().add(new Table("csv2.csv", "10/03/2013", "C:\\CSV_v", "29 KB"));
					getData().add(new Table("csvFile.csv", "28/09/2014", "C:\\files", "39 KB"));
					getData().add(new Table("csvIncercare", "23/12/2013", "C:\\FisiereCSV", "82 KB"));
					setCellValueFactory();
		       }else if(newValue.toString() == TypeInterface.types[4]){
		    	   
		    	    removeContent();
					getData().add(new Table("xls1.xls", "03/11/2013", "C:\\FisiereXLS", "131 KB"));
					getData().add(new Table("xlsFile.xls", "22/06/2014", "C:\\XLSs", "46 KB"));
					getData().add(new Table("xlsVlad.json", "04/07/2014", "C:\\IncercariXLS", "31 KB"));
					getData().add(new Table("fileXls.json", "22/01/2014", "C:\\FisiereLucru", "81 KB"));
					setCellValueFactory();
		       }
		    }
		});
		
		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter(TypeInterface.types[0], "*.xml"),
					new FileChooser.ExtensionFilter(TypeInterface.types[1], "*.xsd"),
					new FileChooser.ExtensionFilter(TypeInterface.types[2], "*.json"),
					new FileChooser.ExtensionFilter(TypeInterface.types[3], "*.csv"),
					new FileChooser.ExtensionFilter(TypeInterface.types[4], "*.xls")
					);
                File file = fileChooser.showOpenDialog(null);
                if(file != null && newValue.toString() !=null){
                	try {
						String path = file.getCanonicalPath();
						String fileName = file.getName();
						
						SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
						String dateModified = dateFormat.format(file.lastModified());
						
						String size = FileUtils.byteCountToDisplaySize(file.length());
						
						String extension="";
						int i = fileName.lastIndexOf('.');
						if(i>0){
							extension = fileName.substring(i+1);
						}
						
						System.out.println(newValue.toString().toLowerCase());
						System.out.println(extension);
						
//						if(newValue.toString().toLowerCase() == extension){
//						System.out.println("-----------");
							getData().add(new Table(fileName, dateModified, path, size));
//						}
					} catch (IOException e) {
						e.printStackTrace();
					}
                }
                System.out.println(file);
			}
		});
	}
	
	
	private void setCellValueFactory() {
		tableName.setCellValueFactory(cellData -> cellData.getValue()
				.nameProperty());
		tableDate.setCellValueFactory(cellData -> cellData.getValue()
				.dateProperty());
		tablePath.setCellValueFactory(cellData -> cellData.getValue()
				.pathProperty());
		tableSize.setCellValueFactory(cellData -> cellData.getValue()
				.sizeProperty());
	}

	private void removeContent() {
		tableViewData.removeAll(getData());
		tableView.getSelectionModel().clearSelection();
	}
	
	
}