package com.token.controller;

import com.gn.App;
import com.sun.javafx.collections.ObservableListWrapper;
import com.token.entity.Repair;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.entity.vo.RepairVO;
import com.token.eunms.RepairStatus;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
import com.token.mapper.RepairMapper;
import com.token.service.DispatchService;
import com.token.service.RepairService;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
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
import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    private TableColumn<User, String> avatar;

    @FXML
    private TableColumn<User, String> status;

    @FXML
    private TableColumn<User, LocalDateTime> createTime;

    @FXML
    private Parent parent;

    @FXML
    private TableView<RepairVO> repairVOTableView;

    ObservableList<RepairVO> repairVOObservableList = FXCollections.observableArrayList();

    SortedList<RepairVO> repairSortedList;

    private Logger log = LoggerFactory.getLogger(UserController.class);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (!ObjectUtils.isEmpty((ServiceHomeUtils.getLoginUserRole()))){
            new Thread(()->{
                repairVOObservableList.clear();
                Repair repair = new Repair();
                if (ServiceHomeUtils.getLoginUserRole() != UserRole.ADMIN) {
                    repair.setUserId(Math.toIntExact(ServiceHomeUtils.getLoginUserInfo().getId()));
                }
                repair.setStatus("");
                List<RepairVO> repairVOListList = SpringUtils.getBean(RepairService.class).repairList(repair);
                repairVOObservableList.addAll(repairVOListList);
                repairSortedList = new SortedList(repairVOObservableList, Comparator.comparing(RepairVO::getCreateTime).reversed());
                repairVOTableView.setItems(repairSortedList);
            }).start();
        }
        showWeb();
        setupColumns();
        updateItem();
    }

    @FXML
    private void selectRepair() {
        Repair repair = new Repair();
        if (ServiceHomeUtils.getLoginUserRole() != UserRole.ADMIN) {
            repair.setUserId(Math.toIntExact(ServiceHomeUtils.getLoginUserInfo().getId()));
        }
        if (!StringUtils.isEmpty(repairIdField.getText())){
            repair.setId(Long.valueOf(repairIdField.getText()));
        }
        repair.setStatus(ServiceHomeUtils.setRepairStatusType(statusBox.getValue()));
        List<RepairVO> repairVOListList = SpringUtils.getBean(RepairService.class).repairList(repair);
        if (!StringUtils.isEmpty(repairNameField.getText())){
            repairVOListList = repairVOListList.stream().filter(item -> {
                if (item.getUserName().contains(repairNameField.getText())) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
        };
        repairVOTableView.setItems(new ObservableListWrapper<RepairVO>(new ArrayList<RepairVO>(repairVOListList)));
    }

    @FXML
    private void insertRepair() {
        try {
            initStage(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void updateRepair() {
        try {
            RepairVO repairVO = this.repairVOTableView.getSelectionModel().getSelectedItem();
            if (repairVO == null) {
                Alerts.warning("未选择", "请先选择要修改的报修信息");
                return;
            }
            if(ServiceHomeUtils.getLoginUserRole().equals(UserRole.STUDENT) && !ServiceHomeUtils.setRepairStatusType(repairVO.getStatus()).equals(RepairStatus.UN_SUBMITTED)){
                Alerts.warning("不可修改", "当前状态不可修改报修信息");
                return;
            }
            initStage(repairVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteRepair() {
        try {
            RepairVO repairVO = this.repairVOTableView.getSelectionModel().getSelectedItem();
            if (repairVO == null) {
                Alerts.warning("未选择", "请先选择要删除的报修信息");
                return;
            }
            repairVO.setDelFlag("1");
            SpringUtils.getBean(RepairService.class).delete(repairVO);
            repairVOObservableList.remove(repairVO);
            Alerts.success("成功", "报修信息删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("失败", "报修信息删除失败");
        }
    }

    @FXML
    private void dispatchBtn() {
        try {
            RepairVO repairVO = this.repairVOTableView.getSelectionModel().getSelectedItem();
            if (repairVO == null) {
                Alerts.warning("未选择", "请先选择要派工的报修信息");
                return;
            }
            if (!repairVO.getStatus().equals("1")){
                Alerts.warning("失败", "你的报修信息未在提交状态");
                return;
            }
            serviceStage(repairVO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showWeb() {
        if (ObjectUtils.isEmpty(ServiceHomeUtils.getLoginUserRole())) {
            return;
        }
        if (ServiceHomeUtils.getLoginUserRole() == UserRole.ADMIN) {
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
        if (ServiceHomeUtils.getLoginUserRole() == UserRole.STUDENT) {
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
        avatar.setCellValueFactory(new PropertyValueFactory<>("avatar"));
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
        controller.setRepairSortedList(repairSortedList);
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
     * 指定维修员窗口
     */
    private void serviceStage(RepairVO repairVO) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/template/repair/service.fxml"));
        StackPane target = loader.load();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        ServiceController controller = loader.getController();
        controller.setStage(stage);
        controller.setRepairVO(repairVO);
        controller.setRepairVOTableView(repairVOTableView);
        stage.setHeight(400);
        stage.setWidth(500);
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
                            String[] urls = url.split(",");
                            Image image = new Image(new File(urls[0]).toURI().toString());
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
                            setText(ServiceHomeUtils.setRepairStatusType(status));
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
