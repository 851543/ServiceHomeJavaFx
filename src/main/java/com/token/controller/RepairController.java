package com.token.controller;

import com.gn.App;
import com.sun.javafx.collections.ObservableListWrapper;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.entity.vo.RepairVO;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class RepairController implements Initializable {

    @FXML
    private TextField repairIdField;

    @FXML
    private Label repairIdLabel;

    @FXML
    private TextField repairNameField;

    @FXML
    private Label repairNameLabel;

    @FXML ComboBox statusBox;

    @FXML
    private TableColumn<User, Long> id;

    @FXML
    private TableColumn<User, String> user_name;

    @FXML
    private TableColumn<User, String> nick_name;

    @FXML
    private TableColumn<User, String> sex;

    @FXML
    private TableColumn<User, String> phone;

    @FXML
    private TableColumn<User, String> floor;

    @FXML
    private TableColumn<User, String> room;

    @FXML
    private TableColumn<User, String> content;

    @FXML
    private TableColumn<User, String> status;

    @FXML
    private TableColumn<User, LocalDateTime> createTime;

    @FXML
    private Parent parent;

    @FXML
    private TableView<RepairVO> repairVOTableView;

    ObservableList<RepairVO> repairVOObservableList = FXCollections.observableArrayList();

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showWeb();
        setupColumns();
        updateItem();
    }

    private void showWeb(){
        if (ObjectUtils.isEmpty(ServiceHomeUtils.getLoginUserRole())){
            return;
        }
        if (ServiceHomeUtils.getLoginUserRole() == UserRole.ADMIN){
            parent = repairIdLabel.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(repairIdLabel);
            }
            parent = repairIdField.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(repairIdField);
            }
            return;
        }
        if (ServiceHomeUtils.getLoginUserRole() == UserRole.STUDENT){
            parent = repairNameLabel.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(repairNameLabel);
            }
            parent = repairNameField.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(repairNameField);
            }
            id.setVisible(true);
            user_name.setVisible(false);
            nick_name.setVisible(false);
            sex.setVisible(false);
            phone.setVisible(false);
        }
    }

    private void setupColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        nick_name.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        floor.setCellValueFactory(new PropertyValueFactory<>("floor"));
        room.setCellValueFactory(new PropertyValueFactory<>("room"));
        content.setCellValueFactory(new PropertyValueFactory<>("content"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        createTime.setCellValueFactory(new PropertyValueFactory<>("createTime"));
    }


    @FXML
    private void selectUser() {
    }

    @FXML
    private void deleteUser() {
    }

    @FXML
    private void updateUser() {

    }

    @FXML
    private void insertUser() {
    }

    /**
     * 窗口
     *
     * @param user
     * @throws IOException
     */
    private void initStage(User user) throws IOException {
    }

    /**
     * 显示类型转换
     */
    private void updateItem() {
        createTime.setCellFactory(param -> {
            TableCell<User, LocalDateTime> cell = new TableCell<User, LocalDateTime>() {
                @Override
                protected void updateItem(LocalDateTime localDateTime, boolean empty) {
                    if (localDateTime == null || empty) {
                        setText(null);
                    } else {
                        try {
                            setText(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime));
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }

            };
            return cell;
        });
        sex.setCellFactory(param -> {
            TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                protected void updateItem(String sex, boolean empty) {
                    if (sex == null || empty) {
                        setText(null);
                    } else {
                        try {
                            setText(ServiceHomeUtils.sexType(sex));
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }

            };
            return cell;
        });
        status.setCellFactory(param -> {
            TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                protected void updateItem(String status, boolean empty) {
                    if (status == null || empty) {
                        setText(null);
                    } else {
                        try {
                            setText(ServiceHomeUtils.setStatusType(status));
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }

            };
            return cell;
        });
    }
}
