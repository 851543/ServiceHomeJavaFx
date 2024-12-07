package com.token.controller;


import com.gn.GNAvatarView;
import com.jfoenix.controls.JFXBadge;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.token.eunms.FxmlView;
import com.token.fx.SpringFXMLLoader;
import com.token.fx.StageManager;
import com.token.utils.SpringUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.SVGPath;
import org.controlsfx.control.PopOver;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * 主页控制器类
 * @author 阿俊
 * @description
 */
@Controller
public class MainController implements Initializable {

    @FXML
    private ScrollPane body;

    @FXML
    private Label title;

    @FXML
    private JFXButton config;

    private Parent popContent;

    public static final PopOver popConfig = new PopOver();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadContentPopup();
    }

    @FXML
    private void openConfig() {
        if (popConfig.isShowing()) {
            popConfig.hide();
        } else {
            popConfig.show(config, 0);
            popConfig.getRoot().setFocusTraversable(true);
        }
    }

    private void loadContentPopup() {
        try {
            popContent = SpringUtils.getBean(SpringFXMLLoader.class).load("/template/main/config.fxml");
            popConfig.getRoot().getStylesheets().add(getClass().getResource("/styles/css/poplight.css").toExternalForm());
            popConfig.setContentNode(popContent);
            popConfig.setArrowLocation(PopOver.ArrowLocation.TOP_RIGHT);
            popConfig.setArrowIndent(0);
            popConfig.setArrowSize(0);
            popConfig.setCornerRadius(0);
            popConfig.setAutoFix(true);
            popConfig.setAnimated(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Parent getPopContent() {
        return popContent;
    }

    public void setPopContent(Parent popContent) {
        this.popContent = popContent;
    }

    @FXML
    private void repairClicked(){
        SpringUtils.getBean(StageManager.class).switchContent(FxmlView.REPAIR,body);
    }

    @FXML
    private void serviceClicked(){
        SpringUtils.getBean(StageManager.class).switchContent(FxmlView.SERVICE,body);
    }

    @FXML
    private void studentClicked(){
        SpringUtils.getBean(StageManager.class).switchContent(FxmlView.STUDENT,body);
    }
}
