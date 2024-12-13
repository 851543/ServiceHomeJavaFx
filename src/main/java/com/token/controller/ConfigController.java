package com.token.controller;

import com.gn.App;
import com.token.eunms.FxmlView;
import com.token.fx.StageManager;
import com.token.utils.SpringUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;

import java.io.IOException;

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
    @FXML
    private void updatePassword() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/template/main/update_password.fxml"));
        StackPane target = loader.load();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        PassWordController controller = loader.getController();
        controller.setStage(stage);
        stage.setHeight(350);
        stage.setWidth(500);
        //设置窗口图标
        stage.getIcons().add(new Image("/styles/img/icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台
        stage.show(); //显示窗口
    }
}
