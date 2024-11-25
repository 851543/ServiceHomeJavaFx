package com.token.fx;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Objects;

import com.token.eunms.FxmlView;
import org.slf4j.Logger;

import com.gn.decorator.GNDecorator;
import com.gn.decorator.options.ButtonType;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class StageManager {

	private static final Logger LOG = getLogger(StageManager.class);
	
	private final Stage primaryStage;
	private final SpringFXMLLoader springFXMLLoader;
	private final GNDecorator decorator;

	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage, GNDecorator decorator) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
		this.decorator = decorator;
		
		decorator.setTitle("DashboardFx");
		decorator.addButton(ButtonType.FULL_EFFECT);
		decorator.initTheme(GNDecorator.Theme.DEFAULT);
		decorator.getStage().setOnCloseRequest(event -> {
//			this.closeAllPopups();
			Platform.exit();
		});
		decorator.getScene().getStylesheets().addAll(
				getClass().getResource("styles/css/fonts.css").toExternalForm(),
				getClass().getResource("styles/css/material-color.css").toExternalForm(),
				getClass().getResource("styles/css/skeleton.css").toExternalForm(),
				getClass().getResource("styles/css/light.css").toExternalForm(),
				getClass().getResource("styles/css/bootstrap.css").toExternalForm(),
				getClass().getResource("styles/css/shape.css").toExternalForm(),
				getClass().getResource("styles/css/typographic.css").toExternalForm(),
				getClass().getResource("styles/css/helpers.css").toExternalForm(),
				getClass().getResource("styles/css/master.css").toExternalForm()
		);
		decorator.setMaximized(true);
		decorator.getStage().getIcons().add(new Image("/icons/logo2.png"));
	}

	public void switchScene(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.fxml());
		decorator.setContent(viewRootNodeHierarchy);
	}

	public void switchContent(final FxmlView view, ScrollPane body) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.fxml());
		body.setContent(viewRootNodeHierarchy);
	}
	

	public void showPopWindow(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.fxml());
		
		Scene scene = prepareScene(viewRootNodeHierarchy);
		primaryStage.setTitle(view.title());
		primaryStage.setScene(scene);
		primaryStage.setHeight(600d);
		primaryStage.setWidth(1000d);
        primaryStage.centerOnScreen();
		try {
			primaryStage.show();
		} catch (Exception exception) {
			logAndExit("Unable to show scene for title" + view.title(), exception);
		}
	}

	private Scene prepareScene(Parent rootnode) {
		Scene scene = primaryStage.getScene();

		if (scene == null) {
			scene = new Scene(rootnode);
		}
		scene.setRoot(rootnode);
		return scene;
	}

	private Parent loadViewNodeHierarchy(String fxmlFilePath) {
		Parent rootNode = null;
		try {
			rootNode = springFXMLLoader.load(fxmlFilePath);
			Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
		} catch (Exception exception) {
			logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
		}
		return rootNode;
	}

	private void logAndExit(String errorMsg, Exception exception) {
		LOG.error(errorMsg, exception, exception.getCause());
		Platform.exit();
	}

	public GNDecorator getDecorator() {
		return decorator;
	}

//	public void closeAllPopups() {
//		if (MainController.popConfig.isShowing())
//			MainController.popConfig.hide();
//		if (MainController.popup.isShowing())
//			MainController.popup.hide();
//	}

	public void showDecorator() {
		decorator.show();
	}
}
