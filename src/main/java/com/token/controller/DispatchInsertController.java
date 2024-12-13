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
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Controller
public class DispatchInsertController {

    @FXML
    private CodeArea faultAnalysis;

    @FXML
    private CodeArea maintenanceProcess;

    @FXML
    private CodeArea repairResults;

    @FXML
    private Label dispatchError;

    private Dispatch dispatch;

    private Stage stage;

    private TableView<Dispatch> dispatchTableView;

    ObservableList<Dispatch> dispatchObservableList;

    private Logger log = LoggerFactory.getLogger(DispatchController.class);

    @Transactional
    @FXML
    public void addDispatch() {
        try {
            if (!ObjectUtils.isEmpty(this.dispatch)) {
                if (validate()) {
                    dispatchError.setVisible(true);
                    return;
                }
                dispatch.setStatus(DispatchStatus.PROCESSED.getCode());
                SpringUtils.getBean(DispatchService.class).update(dispatch);
                RepairVO repairVO = new RepairVO();
                repairVO.setId(Long.valueOf(dispatch.getRepairId()));
                repairVO.setStatus(RepairStatus.REPAIRED.getCode());
                SpringUtils.getBean(RepairService.class).update(repairVO);
            }
            dispatchTableView.refresh();
            stage.close();
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
            Alerts.error("失败", "操作失败");
        }
    }

    private boolean validate() {
        if (StringUtils.isEmpty(faultAnalysis.getText())) {
            return true;
        }
        dispatch.setFaultAnalysis(faultAnalysis.getText());
        if (StringUtils.isEmpty(maintenanceProcess.getText())) {
            return true;
        }
        dispatch.setMaintenanceProcess(maintenanceProcess.getText());
        if (StringUtils.isEmpty(repairResults.getText())) {
            return true;
        }
        dispatch.setRepairResults(repairResults.getText());
        return false;
    }

    @FXML
    private void closeView() {
        this.stage.close();
    }

    public Dispatch getDispatch() {
        return dispatch;
    }

    public void setDispatch(Dispatch dispatch) {
        this.dispatch = dispatch;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public TableView<Dispatch> getDispatchTableView() {
        return dispatchTableView;
    }

    public void setDispatchTableView(TableView<Dispatch> dispatchTableView) {
        this.dispatchTableView = dispatchTableView;
    }

    public ObservableList<Dispatch> getDispatchObservableList() {
        return dispatchObservableList;
    }

    public void setDispatchObservableList(ObservableList<Dispatch> dispatchObservableList) {
        this.dispatchObservableList = dispatchObservableList;
    }
}
