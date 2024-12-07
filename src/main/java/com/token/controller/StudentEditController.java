package com.token.controller;

import com.token.entity.User;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class StudentEditController implements Initializable {

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

    private Stage stage;

    private TableView<User> studentTableView;

    private ObservableList<User> userObservableList;

    private User user;

    private Logger log = LoggerFactory.getLogger(StudentController.class);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    private void addOrEditUser() {
        try {
            if (ObjectUtils.isEmpty(this.user)) {
                boolean validate = userValidate();
                if (validate) {
                    userInfoError.setVisible(true);
                    return;
                }
                User user = new User();
                userInfo(user);
                SpringUtils.getBean(UserService.class).insert(UserRole.STUDENT, user);
                userObservableList.add(user);
            } else {
                boolean validate = userValidate();
                if (validate) {
                    userInfoError.setVisible(true);
                    return;
                }
                userInfo(this.user);
                SpringUtils.getBean(UserService.class).update(user);
                //刷新
                studentTableView.refresh();
            }
            stage.close();
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
            Alerts.error("失败", "操作失败");
        }
    }


    private void userInfo(User user) {
        user.setUserName(nameField.getText());
        user.setNickName(nickNameField.getText());
        user.setSex(ServiceHomeUtils.sexType(sexField.getValue()));
        user.setPhoneNumber(phoneField.getText());
        user.setAvatar(ServiceHomeUtils.avatarImage(avatarField));
        user.setStatus(ServiceHomeUtils.setStatusType(statusField.getValue()));
    }

    private boolean userValidate() {
        if (StringUtils.isEmpty(nameField.getText())) {
            userInfoError.setText("你的学号为空");
            return true;
        } else if (!ObjectUtils.isEmpty(SpringUtils.getBean(UserService.class).selectName(nameField.getText()))) {
            userInfoError.setText("你的学号已经存在");
            return true;
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

    @FXML
    private void imagePicker() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter imageFilter =
                new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif");
        fileChooser.getExtensionFilters().add(imageFilter);
        File file = fileChooser.showOpenDialog(avatarField.getScene().getWindow());
        if (!ObjectUtils.isEmpty(file)) {
            avatarField.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void closeView() {
        this.stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TableView<User> getStudentTableView() {
        return studentTableView;
    }

    public void setStudentTableView(TableView<User> studentTableView) {
        this.studentTableView = studentTableView;
    }

    public ObservableList<User> getUserObservableList() {
        return userObservableList;
    }

    public void setUserObservableList(ObservableList<User> userObservableList) {
        this.userObservableList = userObservableList;
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
                avatarField.setImage(new Image(new File(user.getAvatar()).toURI().toString()));
            }catch (Exception e){
                log.error(e.getMessage());
            }
            statusField.setValue(ServiceHomeUtils.setStatusType(user.getStatus()));
        }
    }

}
