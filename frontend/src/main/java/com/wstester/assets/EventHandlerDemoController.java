package com.wstester.assets;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.stage.FileChooser;

import org.apache.commons.io.FileUtils;
import org.controlsfx.dialog.Dialogs;

public class EventHandlerDemoController {

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
	private TableColumn<Table, String> tableProgress;
	@FXML
	private Button addButton;
	@FXML
	private Button deleteButton;
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
			System.out.println(TypeInterface.types[i]);
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
		this.oldValue = oldValue;
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
							String path = file.getCanonicalPath();
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
						} catch (IOException e) {
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

		// get the selected item value 
		tableView.getSelectionModel().selectedItemProperty()
				.addListener(new ChangeListener<Object>() {
					@Override
					public void changed(ObservableValue<?> observableValue,
							Object oldValue, Object newValue) {
						if (tableView.getSelectionModel().getSelectedItem() != null) {
							TableViewSelectionModel<Table> selectionModel = tableView
									.getSelectionModel();
							ObservableList<?> selectedCells = selectionModel
									.getSelectedCells();
							TablePosition<Object, ?> tablePosition = (TablePosition<Object, ?>) selectedCells
									.get(0);
							Object val = tablePosition.getTableColumn()
									.getCellData(newValue);
							System.out.println("Selected value IS :" + val);
						}

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
		tableProgress.setCellValueFactory(cellData -> cellData.getValue()
				.progressProperty());
	}

}