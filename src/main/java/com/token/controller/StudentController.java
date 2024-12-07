package com.token.controller;

import com.gn.App;
import com.sun.javafx.collections.ObservableListWrapper;
import com.token.entity.User;
import com.token.entity.dto.UserRoleListDTO;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class StudentController implements Initializable {

    @FXML
    private TextField userNameField;

    @FXML
    private TextField nickNameField;

    @FXML
    private TableView<User> studentTableView;

    @FXML
    private TableColumn<User, String> id;

    @FXML
    private TableColumn<User, String> user_name;

    @FXML
    private TableColumn<User, String> nick_name;

    @FXML
    private TableColumn<User, String> sex;

    @FXML
    private TableColumn<User, String> phone;

    @FXML
    private TableColumn<User, String> avatar;

    @FXML
    private TableColumn<User, String> status;

    @FXML
    private TableColumn<User, LocalDateTime> createTime;

    private boolean initializeUserList = true;

    ObservableList<User> userObservableList = FXCollections.observableArrayList();

    private Logger log = LoggerFactory.getLogger(StudentController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateItem();
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        user_name.setCellValueFactory(new PropertyValueFactory<>("userName"));
        nick_name.setCellValueFactory(new PropertyValueFactory<>("nickName"));
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        avatar.setCellValueFactory(new PropertyValueFactory<>("avatar"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        createTime.setCellValueFactory(new PropertyValueFactory<>("createTime"));
        if (initializeUserList) {
            UserRoleListDTO userRoleListDTO = new UserRoleListDTO();
            userRoleListDTO.setRole(UserRole.STUDENT.getRole());
            userObservableList.addAll(SpringUtils.getBean(UserService.class).userRoleList(userRoleListDTO));
            initializeUserList = false;
        }
        studentTableView.setItems(userObservableList);
    }

    @FXML
    private void selectStudent(){
        UserRoleListDTO userRoleListDTO = new UserRoleListDTO();
        userRoleListDTO.setRole(UserRole.STUDENT.getRole());
        userRoleListDTO.setUserName(userNameField.getText());
        userRoleListDTO.setNickName(nickNameField.getText());
        List<User> userList = SpringUtils.getBean(UserService.class).userRoleList(userRoleListDTO);
        studentTableView.setItems(new ObservableListWrapper<User>(new ArrayList<User>(userList)));
    }

    @FXML
    private void deleteStudent(){
        try{
            User user = this.studentTableView.getSelectionModel().getSelectedItem();
            if (user == null){
                Alerts.warning("未选择","请先选择要删除的用户");
                return;
            }
            user.setDelFlag("1");
            SpringUtils.getBean(UserService.class).update(user);
            this.userObservableList.remove(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void updateStudent(){
        try {
            User user = this.studentTableView.getSelectionModel().getSelectedItem();
            if (user == null){
                Alerts.warning("未选择","请先选择要修改的用户");
                return;
            }
            initStage(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insertStudent() {
        try {
            initStage(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 显示类型转换
     */
    public void updateItem(){
        avatar.setCellFactory(param -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);
            TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                protected void updateItem(String url, boolean empty) {
                    if (url == null || empty) {
                        imageView.setImage(null);
                        setGraphic(null);
                    } else {
                        try {
                            Image image = new Image(new File(url).toURI().toString());
                            imageView.setImage(image);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                        }
                    }
                }
            };
            return cell;
        });
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

    /**
     * 窗口
     *
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
