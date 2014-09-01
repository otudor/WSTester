package com.wstester.environment;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.collections.ObservableList;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.swing.tree.TreeNode;

import com.wstester.model.Environment;
import com.wstester.model.MongoService;
import com.wstester.model.MySQLService;
import com.wstester.model.Server;
import com.wstester.model.Service;

public class EnvironmentSearchPresenter implements Initializable
{
    @FXML private Node root;
    //@FXML private TextField searchField;
   // @FXML private ListView<Old> resultsList;
    private ObservableList<Environment> environmentsList;
    @FXML private TreeView<Object> treeView;
    
    private MainPresenter mainPresenter;
    private EnvironmentService environmentService;
    
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }

    public Node getView()
    {
        return root;
    }

    public void setMainPresenter(MainPresenter mainPresenter)
    {
        this.mainPresenter = mainPresenter;
    }

    public void setEnvService(EnvironmentService environmentService)
    {
        this.environmentService = environmentService;
    }

    public void search( ActionEvent event)
    {
    	environmentsList = FXCollections.observableList(environmentService.loadEnvironments());    
        //load the tree also
        loadTreeItems();
    }
    
    public void selectEnvironment( String envUID)
    {
        mainPresenter.showEnvDetail( envUID);
    }

    public void selectServer( String serverUId)
    {
        mainPresenter.showFTPDetail( serverUId);
    }
    
    public void selectMongoService( String serverUID,String mongoUId)
    {
        mainPresenter.showMongoDb( serverUID,mongoUId);
    }
    
    public void selectMySQLService( String serverUID, String mysqlUId)
    {
        mainPresenter.showMySQLDb(serverUID, mysqlUId);
    }
    public String getFirstEnv()
    {
    	return environmentService.getFirstEnv().getID();
    }
    
    public void loadTreeItems() 
    {
    	TreeItem<Object> root = new TreeItem<Object>("");
    	root.setExpanded(true);
    	
    	List<Environment> envs = (List<Environment>) environmentService.loadEnvironments();    	
    	for (Environment env : envs)
    	{
    		TreeItem<Object> envNode = new TreeItem<>(env);    		
    		List<Server> serverlist = env.getServers();
    		
    		if ( serverlist!= null && !serverlist.isEmpty())
    		{
    			for (Server server: serverlist)
    			{
    				TreeItem<Object> serverNode = new TreeItem<>(server);
    				
    				List<Service> services = server.getServices();    				
    				if ( services!= null && !services.isEmpty())
	    				for (Service service: services)
	        			{
	    					TreeItem<Object> serviceNode = new TreeItem<>(service);
	    					serverNode.getChildren().add( serviceNode);
	        			}
    				
    				envNode.getChildren().add( serverNode);
    			}
    		}
    		
    		root.getChildren().add(envNode);
    	}
    	
    	treeView.setShowRoot(false);
    	treeView.setRoot(root);
    	treeView.setEditable(false);
    	treeView.getSelectionModel().setSelectionMode( SelectionMode.SINGLE);
    	treeView.setCellFactory(new Callback<TreeView<Object>,TreeCell<Object>>(){
            @Override
            public TreeCell<Object> call(TreeView<Object> p) {
                return new TreeCellImplementation();
            }
        });
    }


	private final class TreeCellImplementation extends TreeCell<Object> 
    {    	
		private TextField textField;
		
    	public TreeCellImplementation()
    	{
            this.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                	TreeItem<Object> t = getTreeItem();
                	if( t == null) return;
                	
                	//treeView.getSelectionModel().select( t);
                	//int idx = treeView.getSelectionModel().getSelectedIndex();
                	//System.out.println( "selectia este: " + t.getValue().toString() + "; focusIdx: " + idx);
                	
                	if( (event.getClickCount() == 1) && (event.getButton() == MouseButton.PRIMARY))
                	{
	                	if ( getItem() != null)
	                	{
		                	if (getItem().getClass() == Environment.class)
		                    	selectEnvironment( ((Environment) getItem()).getID());
		                    else if ( getItem().getClass() == Server.class)
		                    	selectServer(  ((Server) getItem()).getID());
		                    else if ( getItem().getClass() == MongoService.class)
		                    	
		                    	selectMongoService(  ((Server)getTreeItem().getParent().getValue()).getID(),((MongoService) getItem()).getID());
		                     else if ( getItem().getClass() == MySQLService.class)
		                     {      	 	            
	                    		selectMySQLService(  ((Server)getTreeItem().getParent().getValue()).getID(),((MySQLService) getItem()).getID());
		                     }
	                	}
                	}
                }
            });
    	}
    	
    	@Override
        public void updateItem(Object item, boolean empty) 
        {
            super.updateItem(item, empty);
            
            if (empty)
            {
                setText(null);
                setGraphic(null);
                setContextMenu(null);
            }
            else 
            {
            	/*if( isEditing())
            	{
            		if (textField != null) 
                        textField.setText( getItem().toString());
            		setText(null);
            		setGraphic(textField);
            		System.out.println("updateItem -" + getItem().toString() + "- e editing ");
            	}
            	else */
            	{
            		//System.out.println("updateItem (" + getItem().toString() + ") nu e editing ");
            		setText(getItem() == null ? "" : getItem().toString());
                    setGraphic(getTreeItem().getGraphic());
            	}
                
                if( getItem() != null)
	                if ( getItem().getClass() == Environment.class)
	                {
	                	Environment e = (Environment) getItem();
	                	this.setContextMenu(createEnvironmentContextMenu(e));
	                	
	                }
	                else if ( getItem().getClass() == Server.class)
	                {
	                	Environment env = (Environment)getTreeItem().getParent().getValue();
	                	Server ftp = (Server) getItem();
	                	this.setContextMenu( createFTPServerContextMenu(env, ftp));
	                }
            }
        }
    	
    	@Override
        public void startEdit()
    	{
            super.startEdit();
 
            if (textField == null)
                createTextField();
                
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit()
        {
            super.cancelEdit();
            
            setText( ((Environment) getItem()).getName());
            if( getTreeItem() != null )
            	setGraphic(getTreeItem().getGraphic());
            
            //textField = null;
            treeView.setEditable( false);
        }
 
        private void createTextField() 
        {
            textField = new TextField( ((Environment) getItem()).getName());
            textField.setOnKeyReleased(new EventHandler<KeyEvent>() 
            { 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) 
                    {
                    	Environment e = (Environment) getItem();
                    	e.setName( textField.getText());
                        commitEdit( e);
                    } else if (t.getCode() == KeyCode.ESCAPE) {
                        cancelEdit();
                    }
                }
            });
        }
    }
    
    public ContextMenu createEnvironmentContextMenu( Environment e)
    {
    	final ContextMenu contextMenu = new ContextMenu();
    	MenuItem addServer = new MenuItem("Add server");
    	MenuItem remove = new MenuItem("Remove");
    	contextMenu.getItems().addAll(remove);
    	contextMenu.getItems().addAll(addServer);
    	
    	// Remove
    	remove.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle(ActionEvent event) 
    	    {
    	    	TreeItem<Object> c = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	if( c == null ) return;
    	    	
    	    	int idx = treeView.getSelectionModel().getSelectedIndex();
    	        Environment e = (Environment)(c.getValue());
    	        removeEnvironment(e);
                c.getParent().getChildren().remove(c);

                treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
    	    }
    	});
    	
    	// Remove
    	addServer.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle(ActionEvent event) 
    	    {
    	    	TreeItem<Object> item = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	if( item == null ) return;

    	    	Environment e = (Environment)(item.getValue());
    	    	Server server = environmentService.addServerForEnv(e.getID());
    	    	if (server != null)
    	    	{
    	    		TreeItem<Object> serverNode = new TreeItem<>(server);
    	    		item.getChildren().add( serverNode);
    	    		treeView.getSelectionModel().select( serverNode);
    	    		//show details in right pane
    	    		selectServer( server.getID());
    	    	}
                //treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
    	    }
    	});
    	
    	// rename
    	/*rename.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle(ActionEvent event) 
    	    {
    	    	treeView.setEditable( true);
    	    	treeView.edit( (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem());
    	    }
    	});*/
    	
    	return contextMenu;
    }
    
    public ContextMenu createFTPServerContextMenu( Environment e, Server ftp)
    {
    	final ContextMenu contextMenu = new ContextMenu();
    	MenuItem rem = new MenuItem("Remove" /*+ ftp.getID()*/);
    	contextMenu.getItems().addAll( rem);
    	
    	rem.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle( ActionEvent event) 
    	    {
    	    	TreeItem<Object> c = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	    	int idx = treeView.getSelectionModel().getSelectedIndex();
    	    	if( c == null ) return;    	    	
    	    	
    	    	removeServer(e, ftp);
                c.getParent().getChildren().remove(c);
                               
                treeView.getSelectionModel().select( idx > 0 ? idx-1 : 0);
    	    }
    	});
    	
    	return contextMenu;
    }
    
    public void removeServer( Environment e, Server ftp)
    {
    	environmentService.removeServer( e.getID(), ftp.getID());
    }

    public void removeEnvironment( Environment e)
    {
    	environmentService.removeEnvironmentById( e.getID());
    }
    
    public void addEnvAction( ActionEvent event)
    {
        //to do
    	Environment env = environmentService.createEnvironment("New Environment");
    	TreeItem<Object> node = new TreeItem<>( env);
        treeView.getRoot().getChildren().add(node);
        //root.getChildren().add(envNode);
        treeView.setEditable(true);
        treeView.getSelectionModel().select( node);
        treeView.getFocusModel().focusNext();
        treeView.edit( node);
        selectEnvironment( env.getID());
    }
}