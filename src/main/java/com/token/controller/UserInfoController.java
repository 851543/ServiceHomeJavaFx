package com.token.controller;

import com.gn.App;
import com.gn.GNAvatarView;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.concurrent.TimeUnit;

@Controller
public class UserInfoController {

    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField nickNameField;

    @FXML
    private ComboBox sexField;

    @FXML
    private TextField phoneField;

    @FXML
    private ImageView avatarField;

    @FXML
    private ComboBox statusField;

    @FXML
    private Label userInfoError;

    private Label nameInfoLabel;

    private GNAvatarView avatar;

    private Stage stage;

    private User user;

    private Logger log = LoggerFactory.getLogger(UserEditController.class);

    @FXML
    private void addOrEditUser() {
        try {
            boolean validate = userValidate(false);
            if (validate) {
                userInfoError.setVisible(true);
                return;
            }
            userInfo(this.user);
            SpringUtils.getBean(UserService.class).update(user);
            nameInfoLabel.setText(user.getNickName());
            stage.close();
            Alerts.success("成功", "操作成功");
            new Thread(()->{
                try {
                    Thread.currentThread().sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(()->{
                    String imagePath =  StringUtils.isEmpty(user.getAvatar()) ? ServiceHomeUtils.defaultImagePath : user.getAvatar();
                    Image image = new Image(new File(imagePath).toURI().toString());
                    avatar.setImage(image);
                });
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
            Alerts.error("失败", "操作失败");
        }
    }

    @FXML
    private void closeView() {
        this.stage.close();
    }

    @FXML
    private void imagePicker() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(avatarField.getScene().getWindow());
        if (!ObjectUtils.isEmpty(file)) {
            avatarField.setImage(new Image(file.toURI().toString()));
        }
    }

    private boolean userValidate(boolean nameValid) {
        if (nameValid){
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setUserName(nameField.getText());
            userRoleDTO.setRole(ServiceHomeUtils.getLoginUserRole().getRole());
            if (StringUtils.isEmpty(nameField.getText())) {
                userInfoError.setText("你的" + nameField.getText() + "为空");
                return true;
            } else if (!ObjectUtils.isEmpty(SpringUtils.getBean(UserService.class).selectName(userRoleDTO))) {
                userInfoError.setText("你的" + nameField.getText() + "已经存在");
                return true;
            }
        }
        if (StringUtils.isEmpty(nickNameField.getText())) {
            userInfoError.setText("你的姓名为空");
            return true;
        }
        if (StringUtils.isEmpty(phoneField.getText())) {
            userInfoError.setText("你的手机号为空");
            return true;
        } else if (!ServiceHomeUtils.phoneValidate(phoneField.getText())) {
            userInfoError.setText("你的手机号格式不对");
            return true;
        }
        return false;
    }

    private void userInfo(User user) {
        user.setUserName(nameField.getText());
        user.setNickName(nickNameField.getText());
        user.setSex(ServiceHomeUtils.sexType(sexField.getValue()));
        user.setPhoneNumber(phoneField.getText());
        user.setAvatar(ServiceHomeUtils.avatarImage(avatarField));
        user.setStatus(ServiceHomeUtils.setStatusType(statusField.getValue()));
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user != null) {
            idField.setId(user.getId().toString());
            nameField.setText(user.getUserName());
            nickNameField.setText(user.getNickName());
            sexField.setValue(ServiceHomeUtils.sexType(user.getSex()));
            phoneField.setText(user.getPhoneNumber());
            try {
                String imagePath =  StringUtils.isEmpty(user.getAvatar()) ? ServiceHomeUtils.defaultImagePath : user.getAvatar();
                avatarField.setImage(new Image(new File(imagePath).toURI().toString()));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            statusField.setValue(ServiceHomeUtils.setStatusType(user.getStatus()));
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public GNAvatarView getAvatar() {
        return avatar;
    }

    public void setAvatar(GNAvatarView avatar) {
        this.avatar = avatar;
    }

    public Label getNameInfoLabel() {
        return nameInfoLabel;
    }

    public void setNameInfoLabel(Label nameInfoLabel) {
        this.nameInfoLabel = nameInfoLabel;
    }

}