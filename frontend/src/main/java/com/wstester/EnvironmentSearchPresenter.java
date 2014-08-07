package com.wstester;
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
import com.wstester.model.Server;

public class EnvironmentSearchPresenter implements Initializable
{
    @FXML private Node root;
    //@FXML private TextField searchField;
   // @FXML private ListView<Old> resultsList;
    private ObservableList<Environment> results;
    @FXML private TreeView<Object> treeView;
    
    private MainPresenter mainPresenter;
    private EnvironmentService environmentService;
    
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    	//this.setSelectedEnv( new Long(-1));
    	
        // unfortunately FXML does not currently have a clean way to set a custom CellFactory
        // like we need so we have to manually add this here in code
        /*resultsList.setCellFactory(new Callback<ListView<Old>, ListCell<Old>>()
        {
            public ListCell<Old> call(ListView<Old> contactListView)
            {
                final ListCell<Old> cell = new ListCell<Old>()
                {
                    protected void updateItem(Old contact, boolean empty)
                    {
                        super.updateItem(contact, empty);
                        if ( contact == null || empty)
                        	setText( null);
                        else
                            setText(String.format("%s %s", contact.getName(), contact.getIp()));
                    }
                };
                
                cell.setOnMouseClicked(new EventHandler<Event>()
                {
                    public void handle(Event event)
                    {
                        Old env = cell.getItem();
                        if (env != null)
                        {
                        	//setSelectedEnv( new Long(env.getId()));
                            contactSelected( getSelectedEnv());
                        }
                    }
                });
                
                
                return cell;
            }
        });*/
        
        /*
        treeView.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
		    @Override
		    public void handle(MouseEvent mouseEvent)
		    {            
		        //if(mouseEvent.getClickCount() == 2)
		        {
		            TreeItem<Object> item = treeView.getSelectionModel().getSelectedItem();
		            
		            //System.out.println("Selected Text : " + item.getValue());
		            //System.out.println("Selected class : " + item.getClass().toString());
		            
		            String className = item.getValue().getClass().getSimpleName();
		            	
		            if( className.equals("Old"))
		            {
		            	Old e = (Old)item.getValue();
		            	
                        if (e != null)
                        {
                        	if( mouseEvent.getClickCount() == 2)
                        	{
                        		System.out.println("Dublu click pe un: " + className + ";" + e.getId() + "; " + e.getIp());
                        		contactSelected( e.getId());
                        	}
                        	if( mouseEvent.getButton() == MouseButton.SECONDARY)
                        	{
                        		System.out.println("Click dreapta pe un: " + className + ";" + e.getId() + "; " + e.getIp());
                        	}
                        }
		            }
		            else if ( className.equals("FTPServer"))
		            {
		            	FTPServer f = (FTPServer)item.getValue();
		            	
                        if (f != null)
                        {
                        	if(mouseEvent.getClickCount() == 2)
                        	{
                        		System.out.println("Dublu click pe un: " + className + ";" + f.getId() + "; " + f.getIp());
                        		ftpServerSelected( f.getId());
                        	}
                        }
		            }
		        }
		    }
		});*/
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
        //load the listView
    	results = FXCollections.observableList(environmentService.loadEnvironments());
        //resultsList.setItems(results);
        
        //load the tree also
        loadTreeItems();
    }
    
    public void selectEnvironment( String envUID)
    {
        mainPresenter.showEnvDetail( envUID);
    }

    public void selectFTPServer( String serverUId)
    {
        mainPresenter.showFTPDetail( serverUId);
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
    		List<Server> list = env.getServers();
    		
    		if ( list!= null && !list.isEmpty())
    		{
    			for (Server ftp: list)
    			{
    				envNode.getChildren().add( new TreeItem<>(ftp));
    			}
    		}
    		
    		root.getChildren().add(envNode);
    	}
    	
    	treeView.setShowRoot(false);
    	treeView.setRoot(root);
    	treeView.setEditable(true);
    	treeView.setCellFactory(new Callback<TreeView<Object>,TreeCell<Object>>(){
            @Override
            public TreeCell<Object> call(TreeView<Object> p) {
                return new TreeCellImplemenation();
            }
        });
    }


	private final class TreeCellImplemenation extends TreeCell<Object> 
    {    	
    	public TreeCellImplemenation()
    	{
            this.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                public void handle(MouseEvent event)
                {
                	if( (event.getClickCount() == 1) && (event.getButton() == MouseButton.PRIMARY))
                	{
	                	if ( getItem() != null)
		                	if (getItem().getClass() == Environment.class)
		                    	selectEnvironment( ((Environment) getItem()).getID());
		                    else if ( getItem().getClass() == Server.class)
		                    	selectFTPServer(  ((Server) getItem()).getID());
                	}
                	else 
                	if ( (event.getButton() == MouseButton.SECONDARY) && (event.getClickCount() == 1) ) //single right click
                	{
                		getTreeView().getSelectionModel().select(getTreeItem());
                		//System.out.println("Click dreapta");
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
            }
            else 
            {
                setText(getItem() == null ? "" : getItem().toString());
                setGraphic(getTreeItem().getGraphic());
                
                
                //setContextMenu(((AbstractTreeItem) getTreeItem()).getMenu());
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
                	//System.out.println("Env: " + env.getId() + "; ftp: " + ftp.getId());
                }
            }
        }
    	//((AbstractTreeItem) getTreeItem()).
    }
    
    public ContextMenu createEnvironmentContextMenu( Environment e)
    {
    	final ContextMenu contextMenu = new ContextMenu();
    	MenuItem rem = new MenuItem("Remove env" + e.getID());
    	contextMenu.getItems().addAll( rem);
    	
    	// Remove
    	rem.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle(ActionEvent event) 
    	    {
    	        //System.out.println("Remove ...");
    	        removeEnvironment(e);
    	        TreeItem<Object> c = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
                boolean remove = c.getParent().getChildren().remove(c);
    	    }
    	});
    	
    	// Add ...
    	return contextMenu;
    }
    
    public ContextMenu createFTPServerContextMenu( Environment e, Server ftp)
    {
    	final ContextMenu contextMenu = new ContextMenu();
    	MenuItem rem = new MenuItem("Remove ftp: " + ftp.getID());
    	contextMenu.getItems().addAll( rem);
    	
    	// Remove
    	rem.setOnAction(new EventHandler<ActionEvent>() 
    	{
    	    @Override
    	    public void handle( ActionEvent event) 
    	    {
    	        //System.out.println("Remove ftp server: " + ftp.getId() + " from env: " + e.getId());
    	    	TreeItem<Object> c = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
    	        Server f = (Server)(c.getValue());
    	        System.out.println("selected ftpServer inainte de remove: " + f.getID());

    	        removeServer(e, ftp);
                boolean remove = c.getParent().getChildren().remove(c);
                
                TreeItem<Object> c2 = (TreeItem<Object>)treeView.getSelectionModel().getSelectedItem();
                if( c2 == null )
                	System.out.println("selected ftpServer dupa remove e NULL ");
                else
                {
	    	        Server f2 = (Server)(c2.getValue());
	    	        System.out.println("selected ftpServer dupa remove: " + f2.getID());
    	        
                //if(remove) System.out.println("Remove cu succes."); 
                //else System.out.println("Eroare la remove.");
                }
    	    }
    	});
    	
    	// Add ...
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
}