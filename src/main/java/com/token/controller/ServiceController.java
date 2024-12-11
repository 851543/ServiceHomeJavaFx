package com.token.controller;

import com.token.entity.Dispatch;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.entity.vo.RepairVO;
import com.token.eunms.DispatchStatus;
import com.token.eunms.RepairStatus;
import com.token.eunms.UserRole;
import com.token.fx.utils.Alerts;
import com.token.service.DispatchService;
import com.token.service.RepairService;
import com.token.service.UserService;
import com.token.utils.SpringUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ServiceController implements Initializable {

    @FXML
    private ComboBox service;

    @FXML
    private Label serviceError;

    private Stage stage;

    private RepairVO repairVO;

    private TableView<RepairVO> repairVOTableView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setRole(UserRole.SERVICE.getRole());
        List<User> userList = SpringUtils.getBean(UserService.class).userRoleList(userRoleDTO);
        userList.stream().forEach(item->{
            service.getItems().add(item.getUserName());
        });
    }

    @FXML
    private void addService() {
        try {
            if (ObjectUtils.isEmpty(service.getValue())){
                serviceError.setVisible(true);
               return;
            }
            Dispatch dispatch = new Dispatch();
            dispatch.setRepairId(Math.toIntExact(repairVO.getId()));

            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setUserName(service.getValue().toString());
            userRoleDTO.setRole(UserRole.SERVICE.getRole());
            User user = SpringUtils.getBean(UserService.class).selectName(userRoleDTO);
            dispatch.setServiceId(Math.toIntExact(user.getId()));

            userRoleDTO = new UserRoleDTO();
            userRoleDTO.setUserName(repairVO.getUserName());
            userRoleDTO.setRole(UserRole.STUDENT.getRole());
            user = SpringUtils.getBean(UserService.class).selectName(userRoleDTO);
            dispatch.setStudentId(Math.toIntExact(user.getId()));

            repairVO.setStatus(RepairStatus.ASSIGNED.getCode());
            SpringUtils.getBean(RepairService.class).update(repairVO);

            dispatch.setStatus(DispatchStatus.UN_PROCESSED.getCode());
            SpringUtils.getBean(DispatchService.class).insert(dispatch);

            repairVOTableView.refresh();
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("失败", "操作失败");
        }finally {
            stage.close();
        }
    }

    @FXML
    private void closeView() {
        this.stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TableView<RepairVO> getRepairVOTableView() {
        return repairVOTableView;
    }

    public void setRepairVOTableView(TableView<RepairVO> repairVOTableView) {
        this.repairVOTableView = repairVOTableView;
    }

    public RepairVO getRepairVO() {
        return repairVO;
    }

    public void setRepairVO(RepairVO repairVO) {
        this.repairVO = repairVO;
    }
}
