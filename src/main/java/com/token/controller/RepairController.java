package com.token.controller;

import com.gn.App;
import com.sun.javafx.collections.ObservableListWrapper;
import com.token.entity.Repair;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.entity.vo.RepairVO;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
import com.token.service.RepairService;
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

    @FXML
    private ComboBox statusBox;

    @FXML
    private Button dispatchBtn;

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

    @FXML
    private void selectRepair(){
        Repair repair = new Repair();
        if (ServiceHomeUtils.getLoginUserRole() != UserRole.ADMIN){
            repair.setUserId(Math.toIntExact(ServiceHomeUtils.getLoginUserInfo().getId()));
        }
        List<RepairVO> repairVOListList = SpringUtils.getBean(RepairService.class).repairList(repair);
        repairVOTableView.setItems(new ObservableListWrapper<RepairVO>(new ArrayList<RepairVO>(repairVOListList)));
    }

    @FXML
    private void insertRepair(){
        try {
            initStage(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    @FXML
    private void updateRepair(){
        try {
            initStage(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteRepair(){
        try {
            initStage(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void dispatchBtn(){
        try {
            initStage(null);
        }catch (Exception e){
            e.printStackTrace();
        }
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
            parent = dispatchBtn.getParent();
            if (parent instanceof Pane) {
                ((Pane) parent).getChildren().remove(dispatchBtn);
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

    /**
     * 窗口
     *
     * @param repairVO
     * @throws IOException
     */
    private void initStage(RepairVO repairVO) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/template/repair/repair_edit.fxml"));
        StackPane target = loader.load();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        RepairEditController controller = loader.getController();
        controller.setStage(stage);
        controller.setRepairVOObservableList(repairVOObservableList);
        controller.setRepairVo(repairVO);
        controller.setRepairVOTableView(repairVOTableView);
        stage.setHeight(800);
        stage.setWidth(700);
        //设置窗口图标
        stage.getIcons().add(new Image("/styles/img/icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台
        stage.show(); //显示窗口
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
