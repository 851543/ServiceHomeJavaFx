package com.token.controller;

import com.token.entity.Dispatch;
import com.token.entity.Repair;
import com.token.entity.User;
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
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class DispatchDetailController implements Initializable {

    @FXML
    private TextField nameField;

    @FXML
    private TextField nickNameField;

    @FXML
    private ComboBox sexField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField floorField;

    @FXML
    private TextField roomField;

    @FXML
    private CodeArea contentArea;

    @FXML
    private ListView<String> fileList;

    private Dispatch dispatch;

    private Logger log = LoggerFactory.getLogger(DispatchController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileList.setCellFactory(param -> {
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);

            return new ListCell<String>() {
                @Override
                protected void updateItem(String url, boolean empty) {
                    super.updateItem(url, empty);

                    if (url == null || empty) {
                        imageView.setImage(null);
                        setGraphic(null);
                    } else {
                        try {
                            imageView.setImage(new Image(new File(url).toURI().toString()));
                            setGraphic(imageView);
                        } catch (Exception e) {
                            log.error("Error loading image: {}", e.getMessage());
                        }
                    }
                }
            };
        });
    }

    public Dispatch getDispatch() {
        return dispatch;
    }

    public void setDispatch(Dispatch dispatch) {
        this.dispatch = dispatch;
        if (dispatch != null) {
            Repair repair = new Repair();
            repair.setId(Long.valueOf(dispatch.getRepairId()));
            RepairVO repairVO = SpringUtils.getBean(RepairService.class).repairList(repair).get(0);
            nameField.setText(repairVO.getUserName());
            nickNameField.setText(repairVO.getNickName());
            sexField.setValue(ServiceHomeUtils.sexType(repairVO.getSex()));
            phoneField.setText(repairVO.getPhoneNumber());
            floorField.setText(repairVO.getFloor());
            roomField.setText(repairVO.getRoom());
            try {
                List<String> avatar = Arrays.stream(repairVO.getAvatar().split(",")).collect(Collectors.toList());
                fileList.setItems(FXCollections.observableArrayList(avatar));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            contentArea.appendText(repairVO.getContent());
        }
    }
}
