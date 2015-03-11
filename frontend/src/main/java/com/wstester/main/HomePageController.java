package com.wstester.main;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import com.wstester.elements.Dialog;
import com.wstester.elements.Progress;
import com.wstester.model.TestProject;
import com.wstester.services.common.ServiceLocator;
import com.wstester.services.definition.ICamelContextManager;
import com.wstester.services.definition.IProjectFinder;
import com.wstester.services.definition.ITestProjectActions;
import com.wstester.util.MainConstants;

public class HomePageController implements Initializable, ControlledScreen {

	ScreensController myController;
	public static boolean loadState = false;

	@FXML
	private AnchorPane ancor = new AnchorPane();

	@FXML
	private ImageView newProject;
	@FXML
	private ImageView loadProject;

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		initializeButtons();
		loadNewProject();
		loadExistingProject();
	}

	private void initializeButtons() {

		Image newProjectImage = new Image(MainConstants.NEW_PROJECT_IMAGE.toString());
		newProject.setImage(newProjectImage);

		Image loadProjectImage = new Image(MainConstants.LOAD_PROJECT_IMAGE.toString());
		loadProject.setImage(loadProjectImage);
	}

	private void loadNewProject() {
		
		newProject.setOnMouseClicked(new EventHandler<MouseEvent>() {

			Progress p = new Progress();
			Stage progressStage = new Stage();

			@Override
			public void handle(MouseEvent event) {

				TestProject testProject = new TestProject();

				ICamelContextManager manager = null;
				try {
					manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
					IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
					projectFinder.setProject(testProject);
					p.start(progressStage, manager);
				} catch (Exception e) {
					Dialog.errorDialog("New Test Project could not pe created. Please try again!", MainLauncher.stage);
					e.printStackTrace();
				}

				myController.setScreen(MainConstants.DESKTOP_FXML);
			}
		});
	}

	private void loadExistingProject() {

		loadProject.setOnMouseClicked(new EventHandler<MouseEvent>() {

			Progress p = new Progress();
			Stage progressStage = new Stage();

			@Override
			public void handle(MouseEvent event) {
				
				try {
					FileChooser fileChooser = new FileChooser();
					FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("STEP project files (*.step)", "*.step");
					fileChooser.getExtensionFilters().add(extFilter);
					File file = fileChooser.showOpenDialog(MainLauncher.stage);

					if (file != null) {

						ITestProjectActions actions = ServiceLocator.getInstance().lookup(ITestProjectActions.class);
						TestProject testproject = actions.open(file.getCanonicalPath());

						ICamelContextManager manager = ServiceLocator.getInstance().lookup(ICamelContextManager.class);
						IProjectFinder projectFinder = ServiceLocator.getInstance().lookup(IProjectFinder.class);
						projectFinder.setProject(testproject);

						p.start(progressStage, manager);
						myController.setScreen(MainConstants.DESKTOP_FXML);
						loadState = false;
					}
				} catch (Exception e) {
					Dialog.errorDialog("The Test Project could not be loaded. Please try again!", MainLauncher.stage);
					e.printStackTrace();
				}
			}
		});
	}

	public void setScreenParent(ScreensController screenParent) {
		myController = screenParent;
	}
}