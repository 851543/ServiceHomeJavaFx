package com.token.controller;

import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import animatefx.animation.SlideInLeft;
import com.gn.GNAvatarView;
import com.token.constant.AccountNameConstant;
import com.token.constant.MessageConstant;
import com.token.entity.User;
import com.token.entity.dto.UserRoleDTO;
import com.token.eunms.FxmlView;
import com.token.eunms.UserRole;
import com.token.fx.StageManager;
import com.token.service.RoleService;
import com.token.service.UserService;
import com.token.utils.ServiceHomeUtils;
import com.token.utils.SpringUtils;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 登录控制器类
 *
 * @author 阿俊
 * @description
 */
@Controller
public class LoginController implements Initializable {
    @FXML
    private GNAvatarView avatar; // 头像视图

    @FXML
    private HBox box_username; // 用户名输入框容器
    @FXML
    private HBox box_password; // 密码输入框容器
    @FXML
    private TextField username; // 用户名输入框
    @FXML
    private TextField password; // 密码输入框
    @FXML
    private Button login; // 登录按钮

    @FXML
    private Label lbl_password; // 密码错误提示标签
    @FXML
    private Label lbl_username; // 用户名错误提示标签
    @FXML
    private Label lbl_error; // 错误提示标签

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private UserRole loginRole = UserRole.STUDENT;

    private User loginUser;

    private RotateTransition rotateTransition = new RotateTransition(); // 旋转动画

    /**
     * 初始化方法
     *
     * @param location  URL对象
     * @param resources 资源包
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 初始化旋转动画
        rotateTransition.setNode(avatar);
        rotateTransition.setByAngle(360);
        rotateTransition.setDuration(Duration.seconds(1));
        rotateTransition.setAutoReverse(true);

        // 为密码和用户名添加效果
        addEffect(password);
        addEffect(username);

        // 设置监听器
        setupListeners();
    }

    /**
     *
     */
    @FXML
    private void studentClick() {
        LoginSwitch(UserRole.STUDENT);
    }

    @FXML
    private void adminClick() {
        LoginSwitch(UserRole.ADMIN);
    }

    @FXML
    private void serviceClick() {
        LoginSwitch(UserRole.SERVICE);
    }


    /**
     * 处理登录按钮点击事件
     */
    @FXML
    private void loginAction(ActionEvent event) {
        event.consume();
        Pulse pulse = new Pulse(login);
        pulse.setDelay(Duration.millis(20));
        pulse.play();
        if (validPassword() && validUsername())
            enter();
        else if (ObjectUtils.isEmpty(username) || StringUtils.isEmpty(username.getText())) {
            lbl_username.setVisible(true);
        } else {
            lbl_password.setVisible(true);
        }
    }

    private void LoginSwitch(UserRole login) {
        username.getParent().setStyle("-icon-color : -dark-gray; -fx-border-color : transparent");
        password.getParent().setStyle("-icon-color : -dark-gray; -fx-border-color : transparent");
        lbl_username.setVisible(false);
        lbl_password.setVisible(false);
        if (login == UserRole.STUDENT) {
            lbl_error.setText(AccountNameConstant.STUDENT_ID + "或者密码不对");
            username.setPromptText(AccountNameConstant.STUDENT_ID);
            lbl_username.setText(AccountNameConstant.STUDENT_ID + MessageConstant.VITIATION);
            loginRole = UserRole.STUDENT;
        }
        if (login == UserRole.ADMIN) {
            lbl_error.setText(AccountNameConstant.ADMIN + "或者密码不对");
            username.setPromptText(AccountNameConstant.ADMIN);
            lbl_username.setText(AccountNameConstant.ADMIN + MessageConstant.VITIATION);
            loginRole = UserRole.ADMIN;
        }
        if (login == UserRole.SERVICE) {
            lbl_error.setText(AccountNameConstant.WORK_ID + "或者密码不对");
            username.setPromptText(AccountNameConstant.WORK_ID);
            lbl_username.setText(AccountNameConstant.WORK_ID + MessageConstant.VITIATION);
            loginRole = UserRole.SERVICE;
        }
    }

    /**
     * 处理登录操作
     */
    private void enter() {
        lbl_username.setVisible(false);
        lbl_password.setVisible(false);
        boolean validate = loginValidate();
        if (validate) {
            // 输入有效时隐藏错误提示
            lbl_error.setVisible(true);
            return;
        }
        userService.login(loginUser);
        ServiceHomeUtils.setLoginUserRole(loginRole);
        SpringUtils.getBean(StageManager.class).switchScene(FxmlView.MAIN);
    }

    /**
     * 登陆校验
     */
    private boolean loginValidate() {
        UserRoleDTO userRoleDTO = new UserRoleDTO();
        userRoleDTO.setUserName(username.getText());
        userRoleDTO.setRole(loginRole.getRole());
        User user = userService.selectName(userRoleDTO);
        if (ObjectUtils.isEmpty(user) || StringUtils.isEmpty(user.getUserName())) {
            return true;
        }
        String pws = password.getText();
        if (!roleService.selectRolesUserName(user.getUserName()).stream().anyMatch(role -> role.equals(getRole(loginRole))))
            return true;
        if (!user.getPassword().equals(DigestUtils.md5DigestAsHex(pws.getBytes()))) return true;
        this.loginUser = user;
        return false;
    }

    /**
     * 获取角色
     *
     * @param loginRole
     * @return
     */
    private String getRole(UserRole loginRole) {
        switch (loginRole) {
            case ADMIN:
                return "admin";
            case STUDENT:
                return "student";
            case SERVICE:
                return "service";
            default:
                break;
        }
        return "";
    }

    /**
     * 设置各种监听器
     */
    private void setupListeners() {
        // 为密码输入框添加监听器
        password.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!validPassword()) {
                if (!newValue) {
                    // 显示错误提示并播放动画
                    Flash swing = new Flash(box_password);
                    lbl_password.setVisible(true);
                    new SlideInLeft(lbl_password).play();
                    swing.setDelay(Duration.millis(100));
                    swing.play();
                    box_password.setStyle("-icon-color : -danger; -fx-border-color : -danger");
                } else {
                    // 当失去焦点时隐藏错误提示
                    lbl_password.setVisible(false);
                }
            } else {
                // 输入有效时隐藏错误提示
                lbl_error.setVisible(false);
            }
        });

        // 为用户名输入框添加监听器
        username.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!validUsername()) {
                if (!newValue) {
                    // 显示错误提示并播放动画
                    Flash swing = new Flash(box_username);
                    lbl_username.setVisible(true);
                    new SlideInLeft(lbl_username).play();
                    swing.setDelay(Duration.millis(100));
                    swing.play();
                    box_username.setStyle("-icon-color : -danger; -fx-border-color : -danger");
                } else {
                    // 当失去焦点时隐藏错误提示
                    lbl_username.setVisible(false);
                }
            } else {
                // 输入有效时隐藏错误提示
                lbl_error.setVisible(false);
            }
        });
    }

    /**
     * 为节点添加交互效果
     *
     * @param node 要添加效果的节点
     */
    private void addEffect(Node node) {
        // 添加鼠标按下事件监听器
        node.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            rotateTransition.play();
            Pulse pulse = new Pulse(node.getParent());
            pulse.setDelay(Duration.millis(100));
            pulse.setSpeed(5);
            pulse.play();
            // 更改节点样式
            node.getParent().setStyle("-icon-color : -success; -fx-border-color : -success");
        });

        // 添加焦点状态监听器
        node.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!node.isFocused())
                // 当失去焦点时恢复默认样式
                node.getParent().setStyle("-icon-color : -dark-gray; -fx-border-color : transparent");
            else
                // 当获得焦点时设置成功样式
                node.getParent().setStyle("-icon-color : -success; -fx-border-color : -success");
        });
    }


    /**
     * 验证密码是否合法
     *
     * @return 是否合法
     */
    private boolean validPassword() {
        return !password.getText().isEmpty();
    }

    /**
     * 验证用户名是否合法
     *
     * @return 是否合法
     */
    private boolean validUsername() {
        return !username.getText().isEmpty();
    }
}
