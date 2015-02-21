package com.wstester.environment;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.collections.ObservableList;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLService;
import com.wstester.model.RestService;
import com.wstester.model.Server;
import com.wstester.model.Service;
import com.wstester.model.SoapService;

public class EnvironmentSearchPresenter implements Initializable {
	@FXML
	private Node root;
	// @FXML private TextField searchField;
	// @FXML private ListView<Old> resultsList;
	private ObservableList<Environment> environmentsList;
	@FXML
	private TreeView<Object> treeView;

	private MainPresenter mainPresenter;
	private EnvironmentService environmentService;

	public void initialize(URL url, ResourceBundle resourceBundle) {
	}

	public Node getView() {
		return root;
	}

	public void setMainPresenter(MainPresenter mainPresenter) {
		this.mainPresenter = mainPresenter;
	}

	public void setEnvService(EnvironmentService environmentService) {
		this.environmentService = environmentService;
	}

	public void search(ActionEvent event) {
		environmentsList = FXCollections.observableList(environmentService
				.loadEnvironments());
		// load the tree also
		loadTreeItems();
	}

	public void selectEnvironment(String envUID) {
		mainPresenter.showEnvDetail(envUID);
	}

	public void selectServer(String serverUId) {
		mainPresenter.showFTPDetail(serverUId);
	}

	public void selectMongoService(String serverUID, String mongoUId) {
		mainPresenter.showMongoDb(serverUID, mongoUId);
	}

	public void selectMySQLService(String serverUID, String mysqlUId) {
		mainPresenter.showMySQLDb(serverUID, mysqlUId);
	}

	public void selectSoapService(String serverUID, String soaplUId) {
		mainPresenter.showSoap(serverUID, soaplUId);
	}

	public void selectRestService(String serverUID, String rstUId) {
		mainPresenter.showRest(serverUID, rstUId);
	}

	public String getFirstEnv() {
		return environmentService.getFirstEnv().getId();
	}

	public void loadTreeItems() {
		Node icon = null;

		TreeItem<Object> root = new TreeItem<Object>("");
		root.setExpanded(true);

		List<Environment> envs = (List<Environment>) environmentService
				.loadEnvironments();
		for (Environment env : envs) {
			icon = new ImageView(new Image(getClass().getResourceAsStream(
					"/images/treeIcon_environment.png")));
			TreeItem<Object> envNode = new TreeItem<>(env, icon);
			List<Server> serverlist = env.getServers();

			if (serverlist != null && !serverlist.isEmpty()) {
				for (Server server : serverlist) {
					icon = new ImageView(
							new Image(getClass().getResourceAsStream(
									"/images/treeIcon_server.png")));
					TreeItem<Object> serverNode = new TreeItem<>(server, icon);

					List<Service> services = server.getServices();
					if (services != null && !services.isEmpty())
						for (Service service : services) {
							if (service instanceof MySQLService) {

								icon = new ImageView(
										new Image(
												getClass()
														.getResourceAsStream(
																"/images/treeIcon_MySQL_DB.png")));
							} else if (service instanceof MongoService) {
								icon = new ImageView(
										new Image(
												getClass()
														.getResourceAsStream(
																"/images/treeIcon_Mongo_DB.png")));
							} else if (service instanceof SoapService) {
								icon = new ImageView(
										new Image(
												getClass()
														.getResourceAsStream(
																"/images/treeIcon_Mongo_DB.png")));
							} else if (service instanceof RestService) {
								icon = new ImageView(new Image(getClass()
										.getResourceAsStream(
												"/images/treelcon_Rest.png")));
							}
							TreeItem<Object> serviceNode = new TreeItem<>(
									service, icon);
							serverNode.getChildren().add(serviceNode);
						}

					envNode.getChildren().add(serverNode);
				}
			}

			root.getChildren().add(envNode);
		}

		treeView.setShowRoot(false);
		treeView.setRoot(root);
		treeView.setEditable(false);
		treeView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		treeView.setCellFactory(new Callback<TreeView<Object>, TreeCell<Object>>() {
			@Override
			public TreeCell<Object> call(TreeView<Object> p) {
				return new TreeCellImplementation();
			}
		});
	}

	private final class TreeCellImplementation extends TreeCell<Object> {
		private TextField textField;

		public TreeCellImplementation() {
			this.setOnMouseClicked(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					TreeItem<Object> t = getTreeItem();
					if (t == null)
						return;
					if ((event.getClickCount() == 1)
							&& (event.getButton() == MouseButton.PRIMARY)) {
						if (getItem() != null) {
							if (getItem().getClass() == Environment.class) {
								selectEnvironment(((Environment) getItem())
										.getId());
							} else if (getItem().getClass() == Server.class) {
								selectServer(((Server) getItem()).getId());
							} else if (getItem().getClass() == MongoService.class) {
								selectMongoService(((Server) getTreeItem()
										.getParent().getValue()).getId(),
										((MongoService) getItem()).getId());
							} else if (getItem().getClass() == MySQLService.class) {
								selectMySQLService(((Server) getTreeItem()
										.getParent().getValue()).getId(),
										((MySQLService) getItem()).getId());
							} else if (getItem().getClass() == SoapService.class) {
								selectSoapService(((Server) getTreeItem()
										.getParent().getValue()).getId(),
										((SoapService) getItem()).getId());
							} else if (getItem().getClass() == RestService.class) {
								selectRestService(((Server) getTreeItem()
										.getParent().getValue()).getId(),
										((RestService) getItem()).getId());
							}
						}
					}
				}
			});
		}

		@Override
		public void updateItem(Object item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);
				setContextMenu(null);
			} else {
				if (isEditing()) {
					if (textField != null)
						textField.setText(getItem().toString());
					setText(null);
					setGraphic(textField);
					System.out.println("updateItem -" + getItem().toString()
							+ "- e editing ");
				} else {
					// System.out.println("updateItem (" + getItem().toString()
					// + ") nu e editing ");
					setText(getItem() == null ? "" : getItem().toString());
					setGraphic(getTreeItem().getGraphic());
				}

				if (getItem() != null)
					if (getItem().getClass() == Environment.class) {
						Environment e = (Environment) getItem();
						this.setContextMenu(createEnvironmentContextMenu(e));

					} else if (getItem().getClass() == Server.class) {
						Environment env = (Environment) getTreeItem()
								.getParent().getValue();
						Server ftp = (Server) getItem();
						this.setContextMenu(createFTPServerContextMenu(env, ftp));
					} else if (getItem().getClass() == MySQLService.class) {
						Server srv = (Server) getTreeItem().getParent()
								.getValue();
						Service src = (Service) getItem();
						this.setContextMenu(createServiceContextMenu(srv, src));
					} else if (getItem().getClass() == MongoService.class) {
						Server srv = (Server) getTreeItem().getParent()
								.getValue();
						Service src = (Service) getItem();
						this.setContextMenu(createServiceContextMenu(srv, src));
					} else if (getItem().getClass() == SoapService.class) {
						Server srv = (Server) getTreeItem().getParent()
								.getValue();
						Service src = (Service) getItem();
						this.setContextMenu(createServiceContextMenu(srv, src));
					} else if (getItem().getClass() == RestService.class) {
						Server srv = (Server) getTreeItem().getParent()
								.getValue();
						Service src = (Service) getItem();
						this.setContextMenu(createServiceContextMenu(srv, src));
					}
			}
		}
	}

	public ContextMenu createEnvironmentContextMenu(Environment e) {
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem addServer = new MenuItem("Add server");
		MenuItem remove = new MenuItem("Remove");
		contextMenu.getItems().addAll(remove);
		contextMenu.getItems().addAll(addServer);

		// Remove
		remove.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<Object> c = (TreeItem<Object>) treeView
						.getSelectionModel().getSelectedItem();
				if (c == null)
					return;

				int idx = treeView.getSelectionModel().getSelectedIndex();
				Environment e = (Environment) (c.getValue());
				removeEnvironment(e);
				c.getParent().getChildren().remove(c);

				treeView.getSelectionModel().select(idx > 0 ? idx - 1 : 0);
				
			}
		});

		// Remove
		addServer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<Object> item = (TreeItem<Object>) treeView
						.getSelectionModel().getSelectedItem();
				if (item == null)
					return;

				Environment e = (Environment) (item.getValue());
				Server server = environmentService.addServerForEnv(e.getId());
				if (server != null) {
					Node icon = new ImageView(
							new Image(getClass().getResourceAsStream(
									"/images/treeIcon_server.png")));
					TreeItem<Object> serverNode = new TreeItem<>(server, icon);
					item.getChildren().add(serverNode);
					treeView.getSelectionModel().select(serverNode);
					// show details in right pane
					selectServer(server.getId());
				}
				// treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
			}
		});

		// rename
		/*
		 * rename.setOnAction(new EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) {
		 * treeView.setEditable( true); treeView.edit(
		 * (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem()); }
		 * });
		 */

		return contextMenu;
	}

	public ContextMenu createFTPServerContextMenu(Environment e, Server ftp) {
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem rem = new MenuItem("Remove" /* + ftp.getID() */);
		MenuItem add1 = new MenuItem("Add MySQLService" /* + ftp.getID() */);
		MenuItem add2 = new MenuItem("Add MongoDBService" /* + ftp.getID() */);
		MenuItem add3 = new MenuItem("Add SoapService"/* + ftp.getID() */);
		MenuItem add4 = new MenuItem("Add RestService" /* + ftp.getID() */);
		contextMenu.getItems().addAll(rem, add1, add2, add3, add4);

		rem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<Object> c = (TreeItem<Object>) treeView
						.getSelectionModel().getSelectedItem();
				int idx = treeView.getSelectionModel().getSelectedIndex();
				if (c == null)
					return;

				removeServer(e, ftp);
				c.getParent().getChildren().remove(c);

				treeView.getSelectionModel().select(idx > 0 ? idx - 1 : 0);
			}
		});
		add1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				TreeItem<Object> item = (TreeItem<Object>) treeView
						.getSelectionModel().getSelectedItem();
				if (item == null)
					return;

				Server s = (Server) (item.getValue());
				Service service = environmentService.addMySQLServiceforServ(s
						.getId());

				if (service != null) {
					Node icon = new ImageView(new Image(getClass()
							.getResourceAsStream(
									"/images/treeIcon_MySQL_DB.png")));
					TreeItem<Object> serviceNode = new TreeItem<>(service, icon);
					item.getChildren().add(serviceNode);
					treeView.getSelectionModel().select(serviceNode);
					// show details in right pane
					selectMySQLService(s.getId(), service.getId());
				}
				// treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
			}
		});
		add2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<Object> item = (TreeItem<Object>) treeView
						.getSelectionModel().getSelectedItem();
				if (item == null)
					return;

				Server s = (Server) (item.getValue());
				Service service = environmentService.addMongoServiceforServ(s
						.getId());

				if (service != null) {
					Node icon = new ImageView(new Image(getClass()
							.getResourceAsStream(
									"/images/treeIcon_Mongo_DB.png")));
					TreeItem<Object> serviceNode = new TreeItem<>(service, icon);
					item.getChildren().add(serviceNode);
					treeView.getSelectionModel().select(serviceNode);
					selectMongoService(s.getId(), service.getId());
				}
				// treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
			}
		});
		add3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<Object> item = (TreeItem<Object>) treeView
						.getSelectionModel().getSelectedItem();
				if (item == null)
					return;

				Server s = (Server) (item.getValue());
				Service service = environmentService.addSoapServiceforServ(s
						.getId());

				if (service != null) {
					Node icon = new ImageView(new Image(getClass()
							.getResourceAsStream(
									"/images/treeIcon_Mongo_DB.png")));
					TreeItem<Object> serviceNode = new TreeItem<>(service, icon);
					item.getChildren().add(serviceNode);
					treeView.getSelectionModel().select(serviceNode);
					// show details in right pane
					selectSoapService(s.getId(), service.getId());
				}
				// treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
			}
		});
		add4.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<Object> item = (TreeItem<Object>) treeView
						.getSelectionModel().getSelectedItem();
				if (item == null)
					return;

				Server s = (Server) (item.getValue());
				Service service = environmentService.addRestServiceforServ(s
						.getId());

				if (service != null) {
					Node icon = new ImageView(new Image(getClass()
							.getResourceAsStream("/images/treelcon_Rest.png")));
					TreeItem<Object> serviceNode = new TreeItem<>(service, icon);
					item.getChildren().add(serviceNode);
					treeView.getSelectionModel().select(serviceNode);
					// show details in right pane
					selectRestService(s.getId(), service.getId());
				}
				// treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
			}
		});
		return contextMenu;
	}

	public void removeServer(Environment e, Server ftp) {
		environmentService.removeServer(e.getId(), ftp.getId());
	}

	public void removeEnvironment(Environment e) {
		environmentService.removeEnvironmentById(e.getId());
	}

	public void removeService(Server srv, Service src) {
		environmentService.removeService(srv.getId(), src.getId());
	}

	public void addEnvAction(ActionEvent event) {
		Environment env = environmentService
				.createEnvironment("New Environment");
		Node icon = new ImageView(new Image(getClass().getResourceAsStream(
				"/images/treeIcon_environment.png")));
		TreeItem<Object> node = new TreeItem<>(env, icon);
		treeView.getRoot().getChildren().add(node);
		// root.getChildren().add(envNode);
		treeView.setEditable(true);
		treeView.getSelectionModel().select(node);
		treeView.getFocusModel().focusNext();
		treeView.edit(node);
		selectEnvironment(env.getId());
	}

	public ContextMenu createServiceContextMenu(Server srv, Service src) {
		final ContextMenu contextMenu = new ContextMenu();
		MenuItem rem = new MenuItem("Remove" /* + ftp.getID() */);
		contextMenu.getItems().addAll(rem);

		rem.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				TreeItem<Object> c = (TreeItem<Object>) treeView
						.getSelectionModel().getSelectedItem();
				int idx = treeView.getSelectionModel().getSelectedIndex();
				if (c == null)
					return;

				removeService(srv, src);
				c.getParent().getChildren().remove(c);

				treeView.getSelectionModel().select(idx > 0 ? idx - 1 : 0);
			}
		});
		return contextMenu;
	}

	/*
	 * public void removeService( Environment e, Server ftp) {
	 * environmentService.removeService( e.getID(), ftp.getID()); }
	 */

}
