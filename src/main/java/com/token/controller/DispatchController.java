package com.token.controller;

import com.gn.App;
import com.sun.javafx.collections.ObservableListWrapper;
import com.token.entity.Dispatch;
import com.token.entity.Dispatch;
import com.token.entity.Repair;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.entity.vo.RepairVO;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class DispatchController implements Initializable {

    @FXML
    private TextField dispatchIdField;

    @FXML
    private ComboBox statusBox;

    @FXML
    private TableColumn<Dispatch, String> id;

    @FXML
    private TableColumn<Dispatch, String> repairId;

    @FXML
    private TableColumn<Dispatch, String> faultAnalysis;

    @FXML
    private TableColumn<Dispatch, String> maintenanceProcess;

    @FXML
    private TableColumn<Dispatch, String> repairResults;

    @FXML
    private TableColumn<Dispatch, String> status;

    @FXML
    private TableColumn<Dispatch, LocalDateTime> createTime;

    @FXML
    private TableView<Dispatch> dispatchTableView;

    ObservableList<Dispatch> dispatchObservableList = FXCollections.observableArrayList();

    private Logger log = LoggerFactory.getLogger(DispatchController.class);


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (!ObjectUtils.isEmpty((ServiceHomeUtils.getLoginUserInfo()))){
            new Thread(()->{
                dispatchObservableList.clear();
                Dispatch dispatch = new Dispatch();
                dispatch.setServiceId(Math.toIntExact(ServiceHomeUtils.getLoginUserInfo().getId()));
                List<Dispatch> dispatchList = SpringUtils.getBean(DispatchService.class).List(dispatch);
                dispatchObservableList.addAll(dispatchList);
                SortedList<Dispatch> dispatchSortedList = new SortedList(dispatchObservableList, Comparator.comparing(Dispatch::getCreateTime).reversed());
                dispatchTableView.setItems(dispatchSortedList);
            }).start();
        }
        setupColumns();
        updateItem();
    }

    @FXML
    private void select(){
        Dispatch dispatch = new Dispatch();
        if (!StringUtils.isEmpty(dispatchIdField.getText())){
            dispatch.setId(Long.valueOf(dispatchIdField.getText()));
        }
        dispatch.setServiceId(Math.toIntExact(ServiceHomeUtils.getLoginUserInfo().getId()));
        dispatch.setStatus(ServiceHomeUtils.setDispatchStatusType(statusBox.getValue()));
        List<Dispatch> dispatchList = SpringUtils.getBean(DispatchService.class).List(dispatch);
        dispatchTableView.setItems(new ObservableListWrapper<Dispatch>(new ArrayList<Dispatch>(dispatchList)));
    }

    @FXML
    private void selectDetail(){
        try {
            Dispatch dispatch = this.dispatchTableView.getSelectionModel().getSelectedItem();
            if (dispatch == null) {
                Alerts.warning("未选择", "请先选择要修改的用户");
                return;
            }
            detailStage(dispatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void insert(){
        try {
            Dispatch dispatch = this.dispatchTableView.getSelectionModel().getSelectedItem();
            if (dispatch == null) {
                Alerts.warning("未选择", "请先选择要修改的用户");
                return;
            }
            insertStage(dispatch);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 窗口
     *
     * @param dispatch
     * @throws IOException
     */
    private void detailStage(Dispatch dispatch) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/template/dispatch/dispatch_detail.fxml"));
        StackPane target = loader.load();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        DispatchDetailController controller = loader.getController();
        controller.setDispatch(dispatch);
        stage.setHeight(700);
        stage.setWidth(600);
        //设置窗口图标
        stage.getIcons().add(new Image("/styles/img/icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台
        stage.show(); //显示窗口
    }

    /**
     * 窗口
     *
     * @param dispatch
     * @throws IOException
     */
    private void insertStage(Dispatch dispatch) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/template/dispatch/dispatch_insert.fxml"));
        StackPane target = loader.load();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        DispatchInsertController controller = loader.getController();
        controller.setStage(stage);
        controller.setDispatchObservableList(dispatchObservableList);
        controller.setDispatch(dispatch);
        controller.setDispatchTableView(dispatchTableView);
        stage.setHeight(800);
        stage.setWidth(700);
        //设置窗口图标
        stage.getIcons().add(new Image("/styles/img/icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台
        stage.show(); //显示窗口
    }

    private void setupColumns() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        repairId.setCellValueFactory(new PropertyValueFactory<>("repairId"));
        faultAnalysis.setCellValueFactory(new PropertyValueFactory<>("faultAnalysis"));
        maintenanceProcess.setCellValueFactory(new PropertyValueFactory<>("maintenanceProcess"));
        repairResults.setCellValueFactory(new PropertyValueFactory<>("repairResults"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        createTime.setCellValueFactory(new PropertyValueFactory<>("createTime"));
    }

    /**
     * 显示类型转换
     */
    private void updateItem() {
        createTime.setCellFactory(param -> {
            TableCell<Dispatch, LocalDateTime> cell = new TableCell<Dispatch, LocalDateTime>() {
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
        status.setCellFactory(param -> {
            TableCell<Dispatch, String> cell = new TableCell<Dispatch, String>() {
                @Override
                protected void updateItem(String status, boolean empty) {
                    if (status == null || empty) {
                        setText(null);
                    } else {
                        try {
                            setText(ServiceHomeUtils.setDispatchStatusType(status));
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
