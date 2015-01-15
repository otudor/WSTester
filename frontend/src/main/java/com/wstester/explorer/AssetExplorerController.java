package com.wstester.explorer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.StatusBar;
import org.controlsfx.control.action.Action;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import static javafx.geometry.Orientation.VERTICAL;

public class AssetExplorerController implements Initializable {
	@FXML
	private AnchorPane AnchorPane;
	@FXML
	private TreeView<PathItem> primaryView;
	@FXML
	private TreeView<PathItem> secondaryView;
	@FXML
	private TitledPane myTitledPane;
	@FXML
	private ToolBar tool;
	@FXML
	private HBox notificationBox;
	private Stage myStage;
	private NotificationPane notificationPane;

	public void setStage(Stage stage) {
		myStage = stage;
	}

	private ExecutorService service;
//	private StatusBar statusBar;
	private Property<Boolean> hide;

	public AssetExplorerController() {

		service = Executors.newFixedThreadPool(3);
		primaryView = new TreeView<PathItem>();
		secondaryView = new TreeView<PathItem>();
		tool = new ToolBar();
	//	statusBar = new StatusBar();
		notificationPane = new NotificationPane();

	}
	
	
	public void sendNotification (NotificationTypes type, String notif) {
		if (notificationPane.isShowing()) {

			notificationPane.hide();

		} else {
			String imagePath = AssetExplorerController.class.getResource(
					"Search.png").toExternalForm();

			ImageView image = new ImageView(imagePath);

			notificationPane.setGraphic(image);

			System.out.println("SHOWINGGGGGGGGGGG");
			 notificationPane.setText(notif);
			 notificationPane.show();

		}

	
	}

	@SuppressWarnings("unchecked")
	public void loadTreeItems() {
		// tool.getItems().add(notificationPane);
		System.out.println(tool.isVisible());
		Button showBtn = new Button("Show / Hide");

		showBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				System.out.println("SHowing notification pane");
				if (notificationPane.isShowing()) {

					notificationPane.hide();

				} else {
					String imagePath = AssetExplorerController.class.getResource(
							"Search.png").toExternalForm();

					ImageView image = new ImageView(imagePath);

					notificationPane.setGraphic(image);

					System.out.println("SHOWINGGGGGGGGGGG");
					 notificationPane.setText("Hello World! Using the dark theme");
					 notificationPane.show();

				}

			}

		});
		HBox root = new HBox(300);

		root.setPadding(new Insets(0, 750, 20, 0));
		root.getChildren().addAll(showBtn);
	
		notificationPane.setContent(root);
		tool.getItems().add(notificationPane);

		String imagePath = AssetExplorerController.class.getResource(
				"Search.png").toExternalForm();

		ImageView image = new ImageView(imagePath);

	//	notificationPane.setGraphic(image);

	
		System.out.println("LoadTrees called");
		primaryView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		secondaryView.getSelectionModel()
				.setSelectionMode(SelectionMode.SINGLE);
		
		PathItem pathItem2 = new PathItem(Paths.get((".")));
		Iterable<Path> rootDirectories = FileSystems.getDefault()
				.getRootDirectories();
		String hostName = "computer";
		try {
			hostName = InetAddress.getLocalHost().getHostName();
		} catch (UnknownHostException x) {
		}
		System.out.println(Paths.get(hostName));
		PathItem first = new PathItem(Paths.get(hostName));
		primaryView.setRoot(new TreeItem(
				new PathItem(Paths.get("\\").getRoot())));
		for (Path name : rootDirectories) {

			PathItem pathItem = new PathItem(name);

			primaryView.getRoot().getChildren().add(createNode(pathItem));
			primaryView.setEditable(false);

			primaryView
					.setCellFactory((TreeView<PathItem> p) -> {
						final PathTreeCell cell = new PathTreeCell(myStage);
						setDragDropEvent(myStage, cell);
						return cell;
					});
		}
		System.out.println(primaryView);
		secondaryView.setRoot(createNode(pathItem2));
		secondaryView.setEditable(true);
	/*	secondaryView.setCellFactory((TreeView<PathItem> p) -> {
			final PathTreeCell cell = new PathTreeCell(myStage, messageProp);
			setDragDropEvent(myStage, cell);
			return cell;
		});
		*/
		
		 secondaryView.setCellFactory(new Callback<TreeView<PathItem>,TreeCell<PathItem>>(){
	    

				@Override
				public TreeCell<PathItem> call(TreeView<PathItem> param) {
					final PathTreeCell cell = new PathTreeCell(myStage);
					System.out.println("CallbackEntered");
					setDragDropEvent(myStage, cell);
					return cell;
				}
	        });


	}

	/*
	 * public void setupNotification() {
	 * System.out.println("called notification");
	 * 
	 * notificationPane.setText("Hello World! Using the dark theme");
	 * notificationPane.show("x"); notificationPane.show();
	 * 
	 * notificationPane.getActions().addAll(new Action("Sync", ae -> {
	 * 
	 * // do sync
	 * 
	 * 
	 * 
	 * // then hide...
	 * 
	 * // notificationPane.hide();
	 * 
	 * })); notificationPane.setText("Hello World! Using the dark theme");
	 * 
	 * 
	 * }
	 */

	/*
	 * public Node getControlPanel() {
	 * 
	 * System.out.println("CALLED VBOX CREATION");
	 * 
	 * VBox box = new VBox();
	 * 
	 * box.setSpacing(10);
	 * 
	 * TextField statusTextField = new TextField();
	 * 
	 * statusTextField.setPromptText("Status Text");
	 * 
	 * statusTextField.textProperty().bindBidirectional(
	 * statusBar.textProperty());
	 * 
	 * box.getChildren().add(statusTextField);
	 * 
	 * Button simulateTask = new Button("Start Task");
	 * 
	 * simulateTask.setOnAction(evt -> startTask());
	 * 
	 * box.getChildren().add(simulateTask);
	 * 
	 * Button addLeftItem = new Button("Add Left Item");
	 * 
	 * addLeftItem.setOnAction(evt -> addItem(true));
	 * 
	 * box.getChildren().add(addLeftItem);
	 * 
	 * Button addLeftSeparator = new Button("Add Left Separator");
	 * 
	 * addLeftSeparator.setOnAction(evt -> addSeparator(true));
	 * 
	 * box.getChildren().add(addLeftSeparator);
	 * 
	 * Button addRightItem = new Button("Add Right Item");
	 * 
	 * addRightItem.setOnAction(evt -> addItem(false));
	 * 
	 * box.getChildren().add(addRightItem);
	 * 
	 * Button addRightSeparator = new Button("Add Right Separator");
	 * 
	 * addRightSeparator.setOnAction(evt -> addSeparator(false));
	 * 
	 * box.getChildren().add(addRightSeparator);
	 * 
	 * return box;
	 * 
	 * }
	 * 
	 * private int itemCounter;
	 * 
	 * private void addItem(boolean left) {
	 * 
	 * itemCounter++;
	 * 
	 * Button button = new Button(Integer.toString(itemCounter));
	 * 
	 * if (left) {
	 * 
	 * statusBar.getLeftItems().add(button);
	 * 
	 * } else {
	 * 
	 * statusBar.getRightItems().add(button);
	 * 
	 * }
	 * 
	 * }
	 * 
	 * private void addSeparator(boolean left) {
	 * 
	 * if (left) {
	 * 
	 * statusBar.getLeftItems().add(new Separator(VERTICAL));
	 * 
	 * } else {
	 * 
	 * statusBar.getRightItems().add(new Separator(VERTICAL));
	 * 
	 * }
	 * 
	 * }
	 * 
	 * private void startTask() {
	 * 
	 * Task<Void> task = new Task<Void>() {
	 * 
	 * @Override protected Void call() throws Exception {
	 * 
	 * System.out.println("INSIDE CALL"); updateMessage("Begin File Copy");
	 * 
	 * // Thread.sleep(2500);
	 * 
	 * int max = 10;
	 * 
	 * for (int i = 0; i < max; i++) {
	 * 
	 * updateMessage("Message " + i);
	 * 
	 * updateProgress(i, max);
	 * 
	 * }
	 * 
	 * updateProgress(0, 0); updateMessage("File Copied succesfully");
	 * 
	 * done();
	 * 
	 * return null;
	 * 
	 * }
	 * 
	 * };
	 * 
	 * statusBar.textProperty().bind(task.messageProperty());
	 * 
	 * statusBar.progressProperty().bind(task.progressProperty());
	 * 
	 * // remove bindings again
	 * 
	 * task.setOnSucceeded(event -> {
	 * 
	 * statusBar.textProperty().unbind();
	 * 
	 * statusBar.progressProperty().unbind();
	 * 
	 * });
	 * 
	 * new Thread(task).start();
	 * 
	 * }
	 */
	private void setDragDropEvent(Stage stage, final PathTreeCell cell) {
		// The drag starts on a gesture source
		cell.setOnDragDetected(event -> {
			TreeItem<PathItem> item = cell.getTreeItem();
			if (item != null && item.isLeaf()) {
				Dragboard db = cell.startDragAndDrop(TransferMode.COPY);
				ClipboardContent content = new ClipboardContent();
				List<File> files = Arrays.asList(cell.getTreeItem().getValue()
						.getPath().toFile());
				content.putFiles(files);
				db.setContent(content);
				event.consume();
			}
		});
		// on a Target
		cell.setOnDragOver(event -> {
			TreeItem<PathItem> item = cell.getTreeItem();
			if ((item != null && !item.isLeaf())
					&& event.getGestureSource() != cell
					&& event.getDragboard().hasFiles()) {
				Path targetPath = cell.getTreeItem().getValue().getPath();
				PathTreeCell sourceCell = (PathTreeCell) event
						.getGestureSource();
				final Path sourceParentPath = sourceCell.getTreeItem()
						.getValue().getPath().getParent();
				if (sourceParentPath.compareTo(targetPath) != 0) {
					event.acceptTransferModes(TransferMode.COPY);
				}
			}
			event.consume();
		});
		// on a Target
		cell.setOnDragEntered(event -> {
			TreeItem<PathItem> item = cell.getTreeItem();
			if ((item != null && !item.isLeaf())
					&& event.getGestureSource() != cell
					&& event.getDragboard().hasFiles()) {
				Path targetPath = cell.getTreeItem().getValue().getPath();
				PathTreeCell sourceCell = (PathTreeCell) event
						.getGestureSource();
				final Path sourceParentPath = sourceCell.getTreeItem()
						.getValue().getPath().getParent();
				if (sourceParentPath.compareTo(targetPath) != 0) {
					cell.setStyle("-fx-background-color: powderblue;");
				}
			}
			event.consume();
		});
		// on a Target
		cell.setOnDragExited(event -> {
			cell.setStyle("-fx-background-color: white");
			event.consume();
		});
		// on a Target
		cell.setOnDragDropped(event -> {
			Dragboard db = event.getDragboard();
			boolean success = false;
			if (db.hasFiles()) {
				final Path source = db.getFiles().get(0).toPath();
				final Path target = Paths.get(cell.getTreeItem().getValue()
						.getPath().toAbsolutePath().toString(), source
						.getFileName().toString());
				if (Files.exists(target, LinkOption.NOFOLLOW_LINKS)) {
					Platform.runLater(() -> {
						BooleanProperty replaceProp = new SimpleBooleanProperty();
						CopyModalDialog dialog = new CopyModalDialog(stage,
								replaceProp);
						replaceProp.addListener((
								ObservableValue<? extends Boolean> ov,
								Boolean oldValue, Boolean newValue) -> {
							if (newValue) {
								FileCopyTask task = new FileCopyTask(source,
										target);
								service.submit(task);
							}
						});
					});
				} else {
					FileCopyTask task = new FileCopyTask(source, target);
					service.submit(task);
					task.setOnSucceeded(value -> {
						Platform.runLater(() -> {
							TreeItem<PathItem> item = PathTreeItem
									.createNode(new PathItem(target));
							cell.getTreeItem().getChildren().add(item);
							sendNotification(NotificationTypes.COPY_SUCCESFULL, item.getValue().getPath().getFileName().toString());
						});
					});
				}
				success = true;
			}
			event.setDropCompleted(success);
			event.consume();
		});
		// on a Source
		cell.setOnDragDone(event -> {
			;
		});
	}

	private TreeItem<PathItem> createNode(PathItem pathItem) {
		return PathTreeItem.createNode(pathItem);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Initialize called");
		// loadTreeItems("initial 1", "initial 2", "initial 3");
		loadTreeItems();

	}
}
