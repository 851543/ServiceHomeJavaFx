package com.token.controller;

import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class UserEditController implements Initializable {
    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

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

    private TableView<User> userTableView;

    ObservableList<User> studentObservableList;

    ObservableList<User> serviceObservableList;

    SortedList<User> sortedStudentList;

    public SortedList<User> getSortedStudentList() {
        return sortedStudentList;
    }

    public void setSortedStudentList(SortedList<User> sortedStudentList) {
        this.sortedStudentList = sortedStudentList;
    }

    public SortedList<User> getSortedServiceList() {
        return sortedServiceList;
    }

    public void setSortedServiceList(SortedList<User> sortedServiceList) {
        this.sortedServiceList = sortedServiceList;
    }

    SortedList<User> sortedServiceList;

    private User user;

    private UserRole userRole;

    private Logger log = LoggerFactory.getLogger(UserEditController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userRoleShow();
    }

    @FXML
    private void addOrEditUser() {
        try {
            if (ObjectUtils.isEmpty(this.user)) {
                boolean validate = userValidate(true);
                if (validate) {
                    userInfoError.setVisible(true);
                    return;
                }
                User user = new User();
                userInfo(user);
                SpringUtils.getBean(UserService.class).insert(userRole, user);
                if (userRole == UserRole.STUDENT) {
                    ObservableList<User> source = (ObservableList<User>) sortedStudentList.getSource();
                    source.add(user);
                }
                if (userRole == UserRole.SERVICE) {
                    ObservableList<User> source = (ObservableList<User>) sortedServiceList.getSource();
                    source.add(user);
                }
            } else {
                boolean validate = userValidate(false);
                if (validate) {
                    userInfoError.setVisible(true);
                    return;
                }
                userInfo(this.user);
                SpringUtils.getBean(UserService.class).update(user);
                //刷新
                userTableView.refresh();
            }
            stage.close();
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
            Alerts.error("失败", "操作失败");
        }
    }

    /**
     * 展示
     */
    private void userRoleShow() {
        if (this.userRole == UserRole.STUDENT) {
            nameField.setText("学号");
        }
        if (this.userRole == UserRole.SERVICE) {
            nameField.setText("工号");
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

    private boolean userValidate(boolean nameValid) {
        if (nameValid){
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setUserName(nameField.getText());
            userRoleDTO.setRole(userRole.getRole());
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

    @FXML
    private void closeView() {
        this.stage.close();
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TableView<User> getUserTableView() {
        return userTableView;
    }

    public void setUserTableView(TableView<User> userTableView) {
        this.userTableView = userTableView;
    }

    public ObservableList<User> getStudentObservableList() {
        return studentObservableList;
    }

    public void setStudentObservableList(ObservableList<User> studentObservableList) {
        this.studentObservableList = studentObservableList;
    }

    public ObservableList<User> getServiceObservableList() {
        return serviceObservableList;
    }

    public void setServiceObservableList(ObservableList<User> serviceObservableList) {
        this.serviceObservableList = serviceObservableList;
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
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            statusField.setValue(ServiceHomeUtils.setStatusType(user.getStatus()));
        }
        if (!ObjectUtils.isEmpty(this.user)) {
            nameLabel.setVisible(false);
            nameField.setVisible(false);
        }else {
            nameLabel.setVisible(true);
            nameField.setVisible(true);
        }
    }
}
