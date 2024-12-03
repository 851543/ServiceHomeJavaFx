package com.token.controller;

import com.token.entity.User;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class StudentEditController implements Initializable {

    @FXML
    private TextField idField;

    @FXML
    private TextField userNameField;

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

    private Stage stage;

    private TableView<User> studentTableView;

    private ObservableList<User> userObservableList;

    private User user;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void addOrEditUser(){

    }

    @FXML
    private void imagePicker(){
        File file = new FileChooser().showOpenDialog(avatarField.getScene().getWindow());
        if (!ObjectUtils.isEmpty(file)) {
            avatarField.setImage(new Image(file.toURI().toString()));
        }
    }

    @FXML
    private void closeView(){
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
            // TODO
        }
    }

}
