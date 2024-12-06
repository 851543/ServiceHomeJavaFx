package com.token.controller;

import com.gn.App;
import com.token.entity.User;
import com.token.eunms.UserRole;
import com.token.service.UserService;
import com.token.utils.SpringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class StudentController implements Initializable {

    @FXML
    private TableView<User> studentTableView;

    @FXML
    private TableColumn<User,String> id;

    @FXML
    private TableColumn<User,String> user_name;

    @FXML
    private TableColumn<User,String> nick_name;

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

    private boolean initializeUserList = true;

    ObservableList<User> userObservableList = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        nick_name.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        avatar.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        createTime.setCellValueFactory(new PropertyValueFactory<>("createTime"));
        if (initializeUserList) userObservableList.addAll(SpringUtils.getBean(UserService.class).userRoleList(UserRole.STUDENT,null));initializeUserList = false;
        studentTableView.setItems(userObservableList);
    }

    @FXML
    private void insertStudent(){
        try {
            initStage(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 窗口
     * @param user
     * @throws IOException
     */
    private void initStage(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/template/student/student_edit.fxml"));
        StackPane target = loader.load();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        StudentEditController controller = loader.getController();
        controller.setStage(stage);
        controller.setUserObservableList(userObservableList);
        controller.setUser(user);
        controller.setStudentTableView(studentTableView);
        stage.setHeight(800);
        stage.setWidth(700);
        //设置窗口图标
        stage.getIcons().add(new Image("/styles/img/icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台
        stage.show(); //显示窗口
    }
}
