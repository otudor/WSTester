package com.wstester.explorer;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class PathTreeCell extends TreeCell<PathItem>{
    private TextField textField;
    private Path editingPath;
    private ContextMenu dirMenu = new ContextMenu();
    private ContextMenu fileMenu = new ContextMenu();


    public PathTreeCell(final Stage owner) {
        MenuItem expandMenu = new MenuItem("Expand");
        expandMenu.setOnAction((ActionEvent event) -> {
        	if (getTreeItem().getValue().canAccess()) {
            getTreeItem().setExpanded(true);}
        	else {
        		System.out.println("THIS cannot be expanded");
        		 getTreeItem().setExpanded(false);
        	}
        });
        MenuItem expandAllMenu = new MenuItem("Expand All");
        expandAllMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                expandTreeItem(getTreeItem());
            }
            private void expandTreeItem(TreeItem<PathItem> item) {
                if (item.isLeaf()){
                    return;
                }
                item.setExpanded(true);
                ObservableList<TreeItem<PathItem>> children = item.getChildren();
                children.stream().filter(child -> (!child.isLeaf()))
                    .forEach(child -> expandTreeItem(child));
            }
        });
        MenuItem addMenu = new MenuItem("Add Directory");
        addMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                Path newDir = createNewDirectory();
                if (newDir != null) {
                    TreeItem<PathItem> addItem = PathTreeItem.createNode(new PathItem(newDir));
                    getTreeItem().getChildren().add(addItem);
                }
            }
            private Path createNewDirectory() {
                Path newDir = null;
                while (true) {
                    Path path = getTreeItem().getValue().getPath();
                    newDir = Paths.get(path.toAbsolutePath().toString(), "newDirectory" + String.valueOf(getItem().getCountNewDir()));
                    try {
                        Files.createDirectory(newDir);
                        break;
                    } catch (FileAlreadyExistsException ex) {
                        continue;
                    } catch (IOException ex) {
                        cancelEdit();                       
                        break;
                    }
                }
                    return newDir;
            }
        });
        MenuItem deleteMenu =new MenuItem("Delete");
        deleteMenu.setOnAction((ActionEvent event) -> {
            ObjectProperty<TreeItem<PathItem>> prop = new SimpleObjectProperty<>();
            new ModalDialog(owner, getTreeItem(), prop);
            prop.addListener((ObservableValue<? extends TreeItem<PathItem>> ov, TreeItem<PathItem> oldItem, TreeItem<PathItem> newItem) -> {
                try {
                    Files.walkFileTree(newItem.getValue().getPath(), new VisitorForDelete());
                    if (getTreeItem().getParent() == null){
                        // when the root is deleted how to clear the TreeView???
                    } else {
                        getTreeItem().getParent().getChildren().remove(newItem);
                    }
                } catch (IOException ex) {
                 
                }
            });
        });
        dirMenu.getItems().addAll(expandMenu, expandAllMenu, deleteMenu, addMenu);
        fileMenu.getItems().addAll(deleteMenu);
    }

    @Override
    protected void updateItem(PathItem item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (textField != null) {
                    textField.setText(getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText(getString());
                setGraphic(Imageservice.getInstance().getImageValue(item));
                if (!getTreeItem().isLeaf()&& getTreeItem().getValue().canAccess()) {
                    setContextMenu(dirMenu);
                } else if (getTreeItem().isLeaf()&&getTreeItem().getValue().canAccess()) {
                    setContextMenu(fileMenu);
                }
            }
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        if (textField == null){
            createTextField();
        }
        setText(null);
        setGraphic(textField);
        textField.selectAll();
        if (getItem() == null) {
            editingPath = null;
        } else {
            editingPath =getItem().getPath();
        }
    }

    @Override
    public void commitEdit(PathItem pathItem) {
        // rename the file or directory
        if (editingPath != null) {
            try {
                Files.move(editingPath, pathItem.getPath());
            } catch (IOException ex) {
                cancelEdit();
          
            }
        }
        super.commitEdit(pathItem);
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(getString());
      //  setGraphic(null);
        setGraphic(Imageservice.getInstance().getImageValue(getItem()));
    }


    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }
    
    private void createTextField() {
        textField = new TextField(getString());
        textField.setOnKeyReleased((KeyEvent t) -> {
            if (t.getCode() == KeyCode.ENTER){
                Path path = Paths.get(getItem().getPath().getParent().toAbsolutePath().toString(), textField.getText());
                commitEdit(new PathItem(path));
            } else if (t.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }
}
