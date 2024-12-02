package com.token.controller;

import com.alibaba.druid.pool.vendor.NullExceptionSorter;
import com.token.entity.User;
import com.token.service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class StudentController implements Initializable {

    @FXML
    private TableView<User> studentTableView;

    @FXML
    private TableColumn<User,String> id;

    @FXML
    private TableColumn<User,String> name;

    @FXML
    private TableColumn<User,String> sex;

    @FXML
    private TableColumn<User,String> phone;

    @FXML
    private TableColumn<User,String> avatar;

    @FXML
    private TableColumn<User,String> status;

    @FXML
    private TableColumn<User, LocalDateTime> createTime;

    @Autowired
    private UserService userService;

    ObservableList<User> userObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("userName"));
        name.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        avatar.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        createTime.setCellValueFactory(new PropertyValueFactory<>("createTime"));

        try {
            List<User> userList = userService.userList(null);
            userObservableList.addAll(userList);
        }catch (NullPointerException e){

        }
    }
}
