package com.token.controller;


import com.gn.App;
import com.gn.GNAvatarView;
import com.jfoenix.controls.JFXButton;
import com.token.constant.UserConfigConstant;
import com.token.entity.User;
import com.token.entity.UserConfig;
import com.token.eunms.FxmlView;
import com.token.eunms.LoginStatus;
import com.token.eunms.UserRole;
import com.token.fx.SpringFXMLLoader;
import com.token.fx.StageManager;
import com.token.service.UserConfigService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
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
    private JFXButton config;

    @FXML
    private GNAvatarView avatar;

    @FXML
    private Label nameLabel;

    @FXML
    private Button repair;

    @FXML
    private Button dispatch;

    @FXML
    private Button service;

    @FXML
    private Button student;

    @FXML
    private ToggleGroup  changeStatusGroup;

    private Parent popContent;

    private Parent parent;

    public static final PopOver popConfig = new PopOver();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadContentPopup();
        showWeb();
        if (!ObjectUtils.isEmpty(ServiceHomeUtils.getLoginUserInfo())){
           User loginUserInfo = ServiceHomeUtils.getLoginUserInfo();
           nameLabel.setText(loginUserInfo.getNickName());
           String imagePath =  StringUtils.isEmpty(loginUserInfo.getAvatar()) ? ServiceHomeUtils.defaultImagePath : loginUserInfo.getAvatar();
           Image image = new Image(new File(imagePath).toURI().toString());
           Platform.runLater(()->{
               avatar.setImage(image);
           });

            UserConfig userConfig = SpringUtils.getBean(UserConfigService.class).select(loginUserInfo.getId());
            if (ObjectUtils.isEmpty(userConfig)){
                userConfig = new UserConfig();
                userConfig.setUserId(Math.toIntExact(loginUserInfo.getId()));
                userConfig.setConfigKey(UserConfigConstant.USER_LOGIN_STATUS_KEY);
                userConfig.setConfigName(UserConfigConstant.USER_LOGIN_STATUS_NAME);
                userConfig.setConfigValue(LoginStatus.ONLINE.getCode());
                SpringUtils.getBean(UserConfigService.class).insert(userConfig);
                userConfig = SpringUtils.getBean(UserConfigService.class).select(loginUserInfo.getId());
            }
            if (userConfig.getConfigKey().equals(UserConfigConstant.USER_LOGIN_STATUS_NAME)){
                if (userConfig.getConfigValue().equals(LoginStatus.ONLINE.getValue())){
                    System.out.println("在线语音");
                }
                if (userConfig.getConfigValue().equals(LoginStatus.LEAVE.getValue())){
                    System.out.println("离开语音");
                }
                if (userConfig.getConfigValue().equals(LoginStatus.BUSY.getValue())){
                    System.out.println("忙碌语音");
                }
            }
       }
        changeStatusGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue != null && newValue instanceof RadioButton) {
                    RadioButton selectedButton = (RadioButton) newValue;
                    UserConfig userConfig = new UserConfig();
                    userConfig.setUserId(Math.toIntExact(ServiceHomeUtils.getLoginUserInfo().getId()));
                    userConfig.setConfigKey(UserConfigConstant.USER_LOGIN_STATUS_KEY);
                    userConfig.setConfigValue(ServiceHomeUtils.setUserLoginStatus(selectedButton.getText()));
                    SpringUtils.getBean(UserConfigService.class).update(userConfig);
                }
            }
        });
    }

    @FXML
    private void userInfo(MouseEvent e) throws IOException {
        if(e.getClickCount() == 2){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/template/user/user_info.fxml"));
            StackPane target = loader.load();
            Scene scene = new Scene(target);

            Stage stage = new Stage();//创建舞台；
            UserInfoController controller = loader.getController();
            controller.setStage(stage);
            controller.setUser(ServiceHomeUtils.getLoginUserInfo());
            controller.setNameInfoLabel(nameLabel);
            controller.setAvatar(avatar);
            stage.setHeight(800);
            stage.setWidth(700);
            //设置窗口图标
            stage.getIcons().add(new Image("/styles/img/icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene); //将场景载入舞台
            stage.show(); //显示窗口
        }
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

    private void showWeb(){
        if (ObjectUtils.isEmpty(ServiceHomeUtils.getLoginUserRole())){
            return;
        }
        if (ServiceHomeUtils.getLoginUserRole() == UserRole.ADMIN){
            SpringUtils.getBean(StageManager.class).switchContent(FxmlView.REPAIR,body);
            parent = dispatch.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(dispatch);
            }
        }
        if (ServiceHomeUtils.getLoginUserRole() == UserRole.STUDENT){
            SpringUtils.getBean(StageManager.class).switchContent(FxmlView.REPAIR,body);
            parent = student.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(student);
            }
            parent = service.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(service);
            }
            parent = dispatch.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(dispatch);
            }
        }
        if (ServiceHomeUtils.getLoginUserRole() == UserRole.SERVICE){
            SpringUtils.getBean(StageManager.class).switchContent(FxmlView.DISPATCH,body);
            parent = student.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(student);
            }
            parent = service.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(service);
            }
            parent = repair.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(repair);
            }
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
        ServiceHomeUtils.setChangeRole(UserRole.SERVICE);
        SpringUtils.getBean(StageManager.class).switchContent(FxmlView.USER,body);
    }

    @FXML
    private void studentClicked(){
        ServiceHomeUtils.setChangeRole(UserRole.STUDENT);
        SpringUtils.getBean(StageManager.class).switchContent(FxmlView.USER,body);
    }
}
