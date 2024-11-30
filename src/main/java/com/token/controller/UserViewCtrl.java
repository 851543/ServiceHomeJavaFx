package com.token.controller;

import com.gn.App;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * �û�����
 *
 * @author admin
 */
@Component
public class UserViewCtrl implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

//    @FXML
//    private TableView<User> userTableView;
//    @FXML
//    private TableColumn<User, String> c1;
//    @FXML
//    private TableColumn<User, String> c2;
//    @FXML
//    private TableColumn<User, String> c3;
//    @FXML
//    private TableColumn<User, String> c4;
//
//    ObservableList<User> users = FXCollections.observableArrayList();
//
//    private UserService userService = new UserServiceImpl();
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//        //���� service ���ѯ����
//        List<User> userList = userService.select();
//        users.addAll(userList);
///*
//        users.add(new User(1, "����", "����", new BigDecimal(("100"))));
//        users.add(new User(2, "����", "����", new BigDecimal(("100"))));
//        users.add(new User(3, "����", "����", new BigDecimal(("100"))));
//*/
//        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
//        c2.setCellValueFactory(new PropertyValueFactory<>("name"));
//        c3.setCellValueFactory(new PropertyValueFactory<>("money"));
//        c4.setCellValueFactory(new PropertyValueFactory<>("status"));
//        userTableView.setItems(users);
//    }
//
//    @FXML
//    private void deleteUser() {
//        try {
//            User user = this.userTableView.getSelectionModel().getSelectedItem();
//            if (user == null){
//                Alerts.warning("δѡ��","����ѡ��Ҫɾ�����û�");
//                return;
//            }
//            //ɾ���û�
//            userService.delete(user.getId());
//            this.users.remove(user);
//            Alerts.success("�ɹ�", "ɾ���ɹ�");
//        } catch (Exception e) {
//            e.printStackTrace();
//            Alerts.error("ʧ��","ɾ��ʧ��");
//        }
//    }
//
//    @FXML
//    private void chargeView() {
//        try {
//            User user = this.userTableView.getSelectionModel().getSelectedItem();
//            if (user == null){
//                Alerts.warning("δѡ��","����ѡ��Ҫ��ֵ���û�");
//                return;
//            }
//            initChargeStage(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//        �����û�
//     */
//    @FXML
//    private void frozen() {
//        try {
//            User user = this.userTableView.getSelectionModel().getSelectedItem();
//            if (user == null){
//                Alerts.warning("δѡ��","����ѡ��Ҫ������û�");
//                return;
//            }
//            //�����û�
//            userService.frozen(user.getId());
//            user.setStatus(Constant.USER_FROZEN);
//            userTableView.refresh();
//            Alerts.success("�ɹ�", "����ɹ�");
//        } catch (Exception e){
//            e.printStackTrace();
//            Alerts.error("ʧ��","����ʧ��");
//        }
//
//    }
//
//    /*
//        �޸�
//     */
//    @FXML
//    private void userEditView() {
//        try {
//            User user = this.userTableView.getSelectionModel().getSelectedItem();
//            if (user == null){
//                Alerts.warning("δѡ��","����ѡ��Ҫ�޸ĵ�����");
//                return;
//            }
//
//           initStage(user);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//        ���
//     */
//    @FXML
//    private void userAddView() {
//        try {
//            initStage(null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /*
//        ��ʼ����ֵstage
//     */
//    private void initChargeStage(User user) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(App.class.getResource("/com/bjpowernode/module/user/UserChargeView.fxml"));
//        StackPane target = (StackPane) loader.load();
//        Scene scene = new Scene(target);
//
//        Stage stage = new Stage();//������̨��
//        UserChargeViewCtrl controller = (UserChargeViewCtrl)loader.getController();
//        controller.setStage(stage);
//        controller.setUser(user);
//        controller.setUserTableView(userTableView);
//        stage.setHeight(500);
//        stage.setWidth(400);
//        //���ô���ͼ��
//        stage.getIcons().add(new Image("icon.png"));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(scene); //������������̨��
//        stage.show(); //��ʾ���ڣ�
//    }
//
//    /*
//        ��ʼ��stage
//     */
//    private void initStage(User user) throws IOException {
//        FXMLLoader loader = new FXMLLoader();
//        loader.setLocation(App.class.getResource("/com/bjpowernode/module/user/UserHandleView.fxml"));
//        StackPane target = (StackPane) loader.load();
//        //Scene scene1 = App.getDecorator().getScene();
//        Scene scene = new Scene(target);
//
//
//        Stage stage = new Stage();//������̨��
//        UserHandleViewCtrl controller = (UserHandleViewCtrl)loader.getController();
//        controller.setStage(stage);
//        controller.setUsers(users);
//        controller.setUser(user);
//        controller.setUserTableView(userTableView);
////        stage.setResizable(false);
//        stage.setHeight(500);
//        stage.setWidth(400);
//        //���ô���ͼ��
//        stage.getIcons().add(new Image("icon.png"));
//        stage.initModality(Modality.APPLICATION_MODAL);
//        stage.setScene(scene); //������������̨��
//        stage.show(); //��ʾ���ڣ�
//    }
}
