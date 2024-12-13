package com.token.controller;

import com.gn.App;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.eunms.FxmlView;
import com.token.eunms.UserRole;
import com.token.fx.StageManager;
import com.token.fx.utils.Alerts;
import com.token.service.DispatchService;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

@Controller
public class PassWordController {

    @FXML
    private TextField orlPasswordField;

    @FXML
    private TextField nwePasswordField;

    @FXML
    private Label passwordError;

    private Stage stage;

    @FXML
    private void updatePassword() {
        try {
            if (StringUtils.isEmpty(orlPasswordField.getText()) || StringUtils.isEmpty(nwePasswordField.getText())){
                passwordError.setVisible(true);
                return;
            }
            UserRoleDTO userRoleDTO = new UserRoleDTO();
            userRoleDTO.setUserName(ServiceHomeUtils.getLoginUserInfo().getUserName());
            userRoleDTO.setRole(ServiceHomeUtils.getLoginUserRole().getRole());
            User user = SpringUtils.getBean(UserService.class).selectName(userRoleDTO);
            if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(orlPasswordField.getText().getBytes()))){
                passwordError.setVisible(true);
                return;
            }
            User update_user = new User();
            update_user.setId(user.getId());
            update_user.setPassword(DigestUtils.md5DigestAsHex(nwePasswordField.getText().getBytes()));
            SpringUtils.getBean(UserService.class).update(update_user);
            stage.close();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/template/main/main.fxml"));
            MainController controller = loader.getController();
            controller.popConfig.hide();
            SpringUtils.getBean(StageManager.class).switchScene(FxmlView.LOGIN);
        } catch (Exception e) {
            e.printStackTrace();
            stage.close();
            Alerts.error("失败", "操作失败");
        }
    }

    @FXML
    private void closeView(){
        this.stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
