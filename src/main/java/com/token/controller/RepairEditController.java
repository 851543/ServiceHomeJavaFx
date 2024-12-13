package com.token.controller;

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
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.fxmisc.richtext.CodeArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class RepairEditController implements Initializable {
    @FXML
    private TextField idField;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nickNameLabel;

    @FXML
    private TextField nickNameField;

    @FXML
    private Label sexLabel;

    @FXML
    private ComboBox sexField;

    @FXML
    private Label phoneLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField floorField;

    @FXML
    private TextField roomField;

    @FXML
    private BorderPane avatarPane;

    @FXML
    private CodeArea contentArea;

    @FXML
    private ListView<String> fileList;

    private Button uploadButton;

    @FXML
    private ComboBox statusField;

    @FXML
    private Label repairError;

    private Stage stage;

    private TableView<RepairVO> repairVOTableView;

    ObservableList<RepairVO> repairVOObservableList;

    SortedList<RepairVO> repairSortedList;

    public SortedList<RepairVO> getRepairSortedList() {
        return repairSortedList;
    }

    public void setRepairSortedList(SortedList<RepairVO> repairSortedList) {
        this.repairSortedList = repairSortedList;
    }

    private RepairVO repairVO;

    private UserRole loginUserRole = ServiceHomeUtils.getLoginUserRole();

    private Logger log = LoggerFactory.getLogger(RepairEditController.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (loginUserRole != UserRole.ADMIN) {
            nameLabel.setVisible(false);
            nameField.setVisible(false);
            nickNameLabel.setVisible(false);
            nickNameField.setVisible(false);
            sexLabel.setVisible(false);
            sexField.setVisible(false);
            phoneLabel.setVisible(false);
            phoneField.setVisible(false);
        }

        fileList.setCellFactory(param -> {
            final HBox hbox = new HBox();
            final ImageView imageView = new ImageView();
            imageView.setFitHeight(50);
            imageView.setFitWidth(50);

            final Button deleteButton = new Button("Delete");
            deleteButton.setPrefSize(60, 25); // Increased size for better visibility
            deleteButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");

            return new ListCell<String>() {
                @Override
                protected void updateItem(String url, boolean empty) {
                    super.updateItem(url, empty);

                    if (url == null || empty) {
                        imageView.setImage(null);
                        setGraphic(null);
                    } else {
                        try {
                            deleteButton.setOnMouseClicked(event -> {
                                fileList.getItems().remove(url);
                            });
                            imageView.setImage(new Image(new File(url).toURI().toString()));
                            hbox.getChildren().clear();
                            hbox.getChildren().addAll(imageView, deleteButton);
                            hbox.setAlignment(Pos.CENTER);
                            setGraphic(hbox);
                        } catch (Exception e) {
                            log.error("Error loading image: {}", e.getMessage());
                        }
                    }
                }
            };
        });


    }

    @FXML
    private void addOrEditRepair() {
        try {
            if (ObjectUtils.isEmpty(this.repairVO)) {
                boolean validate = repairValidate(true);
                if (validate) {
                    repairError.setVisible(true);
                    return;
                }
                RepairVO repairVO = new RepairVO();
                repairInfo(repairVO);

                SpringUtils.getBean(RepairService.class).insert(repairVO);
                ObservableList<RepairVO> source = (ObservableList<RepairVO>) repairSortedList.getSource();
                source.add(repairVO);
            } else {
                boolean validate = repairValidate(false);
                if (validate) {
                    repairError.setVisible(true);
                    return;
                }
                repairInfo(this.repairVO);
                SpringUtils.getBean(RepairService.class).update(repairVO);
                //刷新
                repairVOTableView.refresh();
            }
            stage.close();
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
            Alerts.error("失败", "操作失败");
        }
    }


    private void repairInfo(RepairVO repairVO) {
        repairVO.setFloor(floorField.getText());
        repairVO.setRoom(roomField.getText());
        repairVO.setAvatar(ServiceHomeUtils.observableListToString(fileList.getItems()));
        repairVO.setContent(contentArea.getText());
        repairVO.setStatus(ServiceHomeUtils.setRepairStatusType(statusField.getValue()));
        if (loginUserRole !=UserRole.ADMIN) return;
        repairVO.setId(Long.valueOf(idField.getId()));
        repairVO.setUserName(nameField.getText());
        repairVO.setNickName(nickNameField.getText());
        repairVO.setSex(ServiceHomeUtils.sexType(sexField.getValue()));
        repairVO.setPhoneNumber(phoneField.getText());
    }

    private boolean repairValidate(boolean nameValid) {
        if (StringUtils.isEmpty(floorField.getText())) {
            repairError.setText("你的楼号为空");
            return true;
        }
        if (StringUtils.isEmpty(roomField.getText())) {
            repairError.setText("你的房号为空");
            return true;
        }
        if (ObjectUtils.isEmpty(fileList.getItems()) || fileList.getItems().size() == 0) {
            repairError.setText("你的现场图片为空");
            return true;
        }
        if (StringUtils.isEmpty(contentArea.getText())) {
            repairError.setText("你的报修内容为空");
            return true;
        }
        if (loginUserRole !=UserRole.ADMIN) return false;
        if (nameValid){
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setUserName(nameField.getText());
            userRoleDTO.setRole(loginUserRole.getRole());
            if (StringUtils.isEmpty(nameField.getText())) {
                repairError.setText("你的" + nameField.getText() + "为空");
                return true;
            } else if (!ObjectUtils.isEmpty(SpringUtils.getBean(UserService.class).selectName(userRoleDTO))) {
                repairError.setText("你的" + nameField.getText() + "已经存在");
                return true;
            }
        }
        if (StringUtils.isEmpty(nickNameField.getText())) {
            repairError.setText("你的姓名为空");
            return true;
        }
        if (StringUtils.isEmpty(phoneField.getText())) {
            repairError.setText("你的手机号为空");
            return true;
        } else if (!ServiceHomeUtils.phoneValidate(phoneField.getText())) {
            repairError.setText("你的手机号格式不对");
            return true;
        }
        return false;
    }

    @FXML
    private void handleSelectFiles() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(avatarPane.getScene().getWindow());

        if (selectedFiles != null && !selectedFiles.isEmpty()) {
            fileList.getItems().addAll(selectedFiles.stream()
                    .map(File::toString)
                    .collect(Collectors.toList()));
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

    public ObservableList<RepairVO> getRepairVOObservableList() {
        return repairVOObservableList;
    }

    public void setRepairVOObservableList(ObservableList<RepairVO> repairVOObservableList) {
        this.repairVOObservableList = repairVOObservableList;
    }

    public RepairVO getRepairVO() {
        return repairVO;
    }

    public void setRepairVo(RepairVO repairVO) {
        this.repairVO = repairVO;
        if (repairVO != null) {
            idField.setId(repairVO.getId().toString());
            nameField.setText(repairVO.getUserName());
            nickNameField.setText(repairVO.getNickName());
            sexField.setValue(ServiceHomeUtils.sexType(repairVO.getSex()));
            phoneField.setText(repairVO.getPhoneNumber());
            try {
                List<String> avatar = Arrays.stream(repairVO.getAvatar().split(",")).collect(Collectors.toList());
                fileList.setItems(FXCollections.observableArrayList(avatar));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            floorField.setText(repairVO.getFloor());
            roomField.setText(repairVO.getRoom());
            contentArea.appendText(repairVO.getContent());
            statusField.setValue(ServiceHomeUtils.setRepairStatusType(repairVO.getStatus()));
        }
    }
}
