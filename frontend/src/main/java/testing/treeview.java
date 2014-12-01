package testing;



import java.io.File;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class treeview extends Application {
 public static void main(String[] args) {
   launch(args);
 }
 private TreeItem<File> createNode(final File f) {
   return new TreeItem<File>(f) {
       private boolean isLeaf;
       private boolean isFirstTimeChildren = true;
       private boolean isFirstTimeLeaf = true;
        
       @Override public ObservableList<TreeItem<File>> getChildren() {
           if (isFirstTimeChildren) {
               isFirstTimeChildren = false;
               super.getChildren().setAll(buildChildren(this));
           }
           return super.getChildren();
       }

       @Override public boolean isLeaf() {
           if (isFirstTimeLeaf) {
               isFirstTimeLeaf = false;
               File f = (File) getValue();
               isLeaf = f.isFile();
           }

           return isLeaf;
       }

       private ObservableList<TreeItem<File>> buildChildren(TreeItem<File> TreeItem) {
           File f = TreeItem.getValue();
           if (f != null && f.isDirectory()) {
               File[] files = f.listFiles();
               if (files != null) {
                   ObservableList<TreeItem<File>> children = FXCollections.observableArrayList();

                   for (File childFile : files) {
                       children.add(createNode(childFile));
                   }

                   return children;
               }
           }

           return FXCollections.emptyObservableList();
       }
   };
}
 @Override
 public void start(Stage stage) {
   Scene scene = new Scene(new Group());
   stage.setTitle("Sample");
   stage.setWidth(300);
   stage.setHeight(190);

   VBox vbox = new VBox();
   vbox.setLayoutX(20);
   vbox.setLayoutY(20);
   
   TreeItem<File> root = createNode(new File("E:\\"));
   TreeView treeView = new TreeView<File>(root);

   

   vbox.getChildren().add(treeView);
   vbox.setSpacing(10);
   ((Group) scene.getRoot()).getChildren().add(vbox);

   stage.setScene(scene);
   stage.show();
 }
}
