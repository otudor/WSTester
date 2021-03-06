package com.wstester.assets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.apache.commons.io.FileUtils;
import org.controlsfx.dialog.Dialogs;

import com.wstester.model.Asset;
import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.IAssetManager;

/**
 * 
 * @author vdumitrache
 *
 */

public class EventHandlerDemoController {

	private Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

	private AssetModel assetModel;
	private AssetModel newValue;

	private Process process;
	public TextArea primaryTextArea;

	@FXML
	private TableView<Table> tableView;
	@FXML
	private TableColumn<Table, String> tableName;
	@FXML
	private TableColumn<Table, String> tableDate;
	@FXML
	private TableColumn<Table, String> tablePath;
	@FXML
	private TableColumn<Table, String> tableSize;
	@FXML
	private TableColumn<Table, String> tableProgress;
	@FXML
	private Button addButton;
	@FXML
	private Button ed;
	@FXML
	private Button deleteButton;
	@FXML
	private Button editButton;
	@FXML
	private Button pathButton;
	@FXML
	private Stage stageEditor = new Stage(); 
	@FXML
	private Stage stagePath = new Stage(); 
	private Parent root;
	@FXML
	private ListView<AssetModel> listView;

	private ObservableList<AssetModel> listViewData = FXCollections
			.observableArrayList();

	private ObservableList<Table> tableViewDataXml = FXCollections
			.observableArrayList();
	private ObservableList<Table> tableViewDataXsd = FXCollections
			.observableArrayList();
	private ObservableList<Table> tableViewDataJson = FXCollections
			.observableArrayList();
	private ObservableList<Table> tableViewDataCsv = FXCollections
			.observableArrayList();
	private ObservableList<Table> tableViewDataXls = FXCollections
			.observableArrayList();

	public EventHandlerDemoController() {
		// Create some sample data for the ComboBox and ListView

		for (int i = 0; i < TypeInterface.types.length; i++) {
			assetModel = new AssetModel(TypeInterface.types[i]);
			listViewData.add(assetModel);
		}
	}

	public ObservableList<Table> getTableDataXml() {
		return tableViewDataXml;
	}

	public ObservableList<Table> getTableDataXsd() {
		return tableViewDataXsd;
	}

	public ObservableList<Table> getTableDataJson() {
		return tableViewDataJson;
	}

	public ObservableList<Table> getTableDataCsv() {
		return tableViewDataCsv;
	}

	public ObservableList<Table> getTableDataXls() {
		return tableViewDataXls;
	}

	public ObservableList<AssetModel> getListData() {
		return listViewData;
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	private void linkValues(AssetModel oldValue, AssetModel newValue) {
		this.newValue = newValue;
	}

	@FXML
	private void initialize() {

		// Init ListView and listen for selection changes

		// tableView.setItems(tableViewDataXml);
		listView.setItems(listViewData);

		listView.getSelectionModel().selectedItemProperty()
		.addListener(new ChangeListener<AssetModel>() {

			@Override
			public void changed(
					ObservableValue<? extends AssetModel> observable,
					AssetModel oldValue, AssetModel newValue) {
				// add data for xml files

				linkValues(oldValue, newValue);

				if (newValue.toString() == TypeInterface.types[0]) {
					setCellValueFactory();
					tableView.setItems(tableViewDataXml);
				} else if (newValue.toString() == TypeInterface.types[1]) {
					setCellValueFactory();
					tableView.setItems(tableViewDataXsd);
				} else if (newValue.toString() == TypeInterface.types[2]) {
					setCellValueFactory();
					tableView.setItems(tableViewDataJson);
				} else if (newValue.toString() == TypeInterface.types[3]) {
					setCellValueFactory();
					tableView.setItems(tableViewDataCsv);
				} else if (newValue.toString() == TypeInterface.types[4]) {
					setCellValueFactory();
					tableView.setItems(tableViewDataXls);
				}
			}
		});

		//de sters dupa ce termin de facut load + set text area 
		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("apasat buton edit");
				stageEditor= new Stage();
				try {
					root = FXMLLoader.load(getClass().getResource("/fxml/assets/editor.fxml"));
					Scene second = new Scene(root);
//					second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());
					stageEditor.setScene(second);
					stageEditor.setTitle("editor");
					stageEditor.show();







				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 



			}
			});  


		pathButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("apasat buton edit");
				stagePath= new Stage();
				try {
					root = FXMLLoader.load(getClass().getResource("/fxml/assets/path.fxml"));
					Scene seconda = new Scene(root);
//					seconda.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());
					stagePath.setScene(seconda);
					stagePath.setTitle("path");
					stagePath.show();



				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 


			}
			});




		pathButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("lalal");
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("EXE files (*.exe)", "*.exe");
				fileChooser.getExtensionFilters().add(extFilter);

				File file = fileChooser.showSaveDialog(stageEditor);
			}
			}); 

		addButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (listView.getSelectionModel().getSelectedItem() != null) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.getExtensionFilters().addAll(
							new FileChooser.ExtensionFilter(
									TypeInterface.types[0], "*.xml"),
									new FileChooser.ExtensionFilter(
											TypeInterface.types[1], "*.xsd"),
											new FileChooser.ExtensionFilter(
													TypeInterface.types[2], "*.json"),
													new FileChooser.ExtensionFilter(
															TypeInterface.types[3], "*.csv"),
															new FileChooser.ExtensionFilter(
																	TypeInterface.types[4], "*.xls"));
					File file = fileChooser.showOpenDialog(null);
					if (file != null && newValue.toString() != null) {
						try {
							String absolutePath = file.getAbsolutePath();
							String path = absolutePath.substring(0,absolutePath.lastIndexOf(File.separator));
							String fileName = file.getName();

							SimpleDateFormat dateFormat = new SimpleDateFormat(
									"MM/dd/yyyy");
							String dateModified = dateFormat.format(file
									.lastModified());

							String size = FileUtils.byteCountToDisplaySize(file
									.length());

							String Tableprogress = new String("Complete");

							String extension = "";
							int i = fileName.lastIndexOf('.');

							if (i > 0) {
								extension = fileName.substring(i + 1);
							}
							
							IAssetManager assetManager= ServiceLocator.getInstance().lookup(IAssetManager.class);
							
							Asset asset = new Asset();
							asset.setName(fileName);
							asset.setPath(path);
							asset.setLastmodified(file.lastModified());
							asset.setSize(file.length());
							asset.setType(extension);
							assetManager.addAsset(asset);
							assetManager.waitUntilFileCopied(asset);			
							TestProject testProject = new TestProject();
							testProject.addAsset(asset);
							
							if (newValue.toString().toLowerCase()
									.equals(extension.toString().toLowerCase())) {
								if (newValue
										.toString()
										.toLowerCase()
										.equals(TypeInterface.types[0]
												.toLowerCase())) {
									getTableDataXml().add(
											new Table(fileName, dateModified,
													path, size, Tableprogress));
								} else if (newValue
										.toString()
										.toLowerCase()
										.equals(TypeInterface.types[1]
												.toLowerCase())) {
									getTableDataXsd().add(
											new Table(fileName, dateModified,
													path, size, Tableprogress));
								} else if (newValue
										.toString()
										.toLowerCase()
										.equals(TypeInterface.types[2]
												.toLowerCase())) {
									getTableDataJson().add(
											new Table(fileName, dateModified,
													path, size, Tableprogress));
								} else if (newValue
										.toString()
										.toLowerCase()
										.equals(TypeInterface.types[3]
												.toLowerCase())) {
									getTableDataCsv().add(
											new Table(fileName, dateModified,
													path, size, Tableprogress));
								} else if (newValue
										.toString()
										.toLowerCase()
										.equals(TypeInterface.types[4]
												.toLowerCase())) {
									getTableDataXls().add(
											new Table(fileName, dateModified,
													path, size, Tableprogress));
								} else if (newValue
										.toString()
										.toLowerCase()
										.equals(TypeInterface.types[5]
												.toLowerCase())) {
									getTableDataXls().add(
											new Table(fileName, dateModified,
													path, size, Tableprogress));
								}
							} else {
								Dialogs.create()
								.lightweight()
								.owner(null)
								.title("Error")
								.message(
										"Please select "
												+ newValue.toString()
												+ " file format")
												.showError();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} else {
					try {
						// dialog from controlsfx jar
						Dialogs.create()
						.lightweight()
						.owner(null)
						.title("Error")
						.message(
								"Please select an asset where you want to add data!")
								.showError();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});


		editButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("apasat buton edit");
				if (listView != null){
					if (listView.getSelectionModel().getSelectedItem() != null){
						System.out.println("selectat");
				stageEditor= new Stage();
				try {
					root = FXMLLoader.load(getClass().getResource("/fxml/assets/editor.fxml"));
					Scene second = new Scene(root);
//					second.getStylesheets().add(WsTesterMain.class.getResource("/styles/application.css").toExternalForm());
					stageEditor.setScene(second);
					stageEditor.setTitle("editor");
					stageEditor.show();







				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 



			}
					else {
						try {
							// dialog from controlsfx jar
							Dialogs.create()
									.lightweight()
									.owner(null)
									.title("Error")
									.message(
											"Please select a file that you want to edit!")
									.showError();
						} catch (Exception e) {
							e.printStackTrace();
						}
					 }
				}
			}
			});

		ed.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (listView != null){
					if (listView.getSelectionModel().getSelectedItem() != null) {

						String tableNamee = tableName.getCellData(tableView.getSelectionModel().getSelectedItem());
						try {
							Group root = new Group();
							Stage stage = new Stage();

							FXMLLoader fxmlLoader = new FXMLLoader();	        
							AnchorPane frame = (AnchorPane) fxmlLoader.load(getClass().getResource("/fxml/assets/editor.fxml").openStream());
							EditController editController = (EditController) fxmlLoader.getController();
							editController.updatePage(tableNamee);
							root.getChildren().add(frame);
							Scene scene = new Scene(root);

							stage.setScene(scene);
							stage.show();
						} catch (IOException e) {
							LOGGER.log(java.util.logging.Level.SEVERE, this.getClass().getSimpleName() + ": Exception on system: " + e);
						} 
					}
					else {
						try {
							// dialog from controlsfx jar
							Dialogs.create()
							.lightweight()
							.owner(null)
							.title("Error")
							.message(
									"Please select a file that you want to edit!")
									.showError();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				}

			}

		});

		deleteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (tableView.getSelectionModel().getSelectedItem() != null) {
					// getting and removing the selected item
					tableView.getItems().remove(
							tableView.getSelectionModel().getSelectedIndex());
				} else {
					try {
						// dialog from controlsfx jar
						Dialogs.create()
						.lightweight()
						.owner(null)
						.title("Error")
						.message(
								"Please select a file and then perform delete actions!")
								.showError();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void setCellValueFactory() {/*
		tableName.setCellValueFactory(cellData -> cellData.getValue()
				.nameProperty());
		tableDate.setCellValueFactory(cellData -> cellData.getValue()
				.dateProperty());
		tablePath.setCellValueFactory(cellData -> cellData.getValue()
				.pathProperty());
		tableSize.setCellValueFactory(cellData -> cellData.getValue()
				.sizeProperty());
		tableProgress.setCellValueFactory(cellData -> cellData.getValue()
				.progressProperty());
	*/}

	private Process openSelectedFile(String editorPath, String filePath) throws IOException {
		Runtime runtime = Runtime.getRuntime();
		return runtime.exec(editorPath + " " + filePath);	


	}
	public void shutDown() {
		if(process != null) {
			process.destroy();
		}
	}
}