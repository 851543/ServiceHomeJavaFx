package com.token.controller;

import com.gn.App;
import com.token.eunms.FxmlView;
import com.token.fx.StageManager;
import com.token.utils.SpringUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.stereotype.Controller;

@Controller
public class ConfigController {

    @FXML
    private void logOut(){
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/template/main/main.fxml"));
        MainController controller = loader.getController();
        controller.popConfig.hide();
        SpringUtils.getBean(StageManager.class).switchScene(FxmlView.LOGIN);
    }
}
