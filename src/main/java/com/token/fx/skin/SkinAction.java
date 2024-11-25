package com.token.fx.skin;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Cursor;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/**
 * SkinAction是一个抽象类,继承自TextFieldSkin。
 * 它用于创建自定义的TextField或PasswordField的皮肤。
 */
public abstract class SkinAction extends TextFieldSkin {

    /**
     * 用于存储按钮和图形元素的StackPane。
     */
    private StackPane button;

    /**
     * 用于存储图形元素的Region。
     */
    private Region graphic;

    /**
     * 存储TextField或PasswordField实例。
     */
    private TextField textField;
    /**
     * 存储PasswordField实例。
     */
    private PasswordField passwordField;

    /**
     * 构造函数,用于初始化SkinAction实例。
     * @param textField TextField实例
     */
    SkinAction(TextField textField) {
        super(textField);
        this.textField = textField;
        config();
        setupListeners();
    }

    /**
     * 构造函数,用于初始化SkinAction实例。
     * @param passwordField PasswordField实例
     */
    SkinAction(PasswordField passwordField) {
        super(passwordField);
        this.passwordField = passwordField;
        config();
        setupListeners();
    }

    /**
     * 配置按钮和图形元素。
     */
    private void config() {
        button = new StackPane();
        button.getStyleClass().setAll("action-button");
        button.setFocusTraversable(false);

        graphic = new Region();
        graphic.getStyleClass().setAll("action-button-graphic");
        graphic.setFocusTraversable(false);

        button.setMinWidth(0D);
        button.setVisible(false);

        button.getChildren().add(graphic);
        ((Pane) getChildren().get(0)).getChildren().add(button);
    }

    /**
     * 设置监听器。
     */
    private void setupListeners() {
        final TextField textField = getSkinnable();

        // 添加鼠标事件监听器
        button.setOnMouseReleased(event -> mouseReleased());
        button.setOnMousePressed(event -> mousePressed());
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(graphic.isVisible()) button.setCursor(Cursor.HAND);
            else button.setCursor(Cursor.DEFAULT);
        });
        button.setOnMouseMoved(event -> {
            if(graphic.isVisible()) button.setCursor(Cursor.HAND);
            else button.setCursor(Cursor.DEFAULT);
        });

        // 添加文本变化监听器
        textField.textProperty().addListener((observable, oldValue, newValue) -> textChanged());
        textField.focusedProperty().addListener((observable, oldValue, newValue) -> focusChanged());

        // 设置按钮和图形元素的最小尺寸
        button.setMinWidth(10);
        button.setMinHeight(10);
        graphic.setMinWidth(10);
        graphic.setMinHeight(10);
    }

    /**
     * 覆盖父类的layoutChildren方法。
     * @param x x坐标
     * @param y y坐标
     * @param w 宽度
     * @param h 高度
     */
    @Override
    protected void layoutChildren(double x, double y, double w, double h) {
        super.layoutChildren(x, y, w, h);

        // 将按钮布局在右侧中央
        layoutInArea(button,
                (x + w) - (button.getWidth() * 2), h/2 - snappedTopInset(),
                10, 10, 0, HPos.RIGHT, VPos.CENTER);
    }

    /**
     * 获取按钮实例。
     * @return StackPane按钮实例
     */
    public StackPane getButton() {
        return button;
    }

    /**
     * 获取图形元素实例。
     * @return Region图形元素实例
     */
    public Region getGraphic() {
        return graphic;
    }

    /**
     * 获取TextField实例。
     * @return TextField实例
     */
    TextField getTextField() {
        return textField;
    }

    /**
     * 获取PasswordField实例。
     * @return PasswordField实例
     */
    PasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * 抽象方法,子类需要实现鼠标释放事件处理逻辑。
     */
    abstract void mouseReleased();

    /**
     * 抽象方法,子类需要实现文本变化事件处理逻辑。
     */
    abstract void textChanged();

    /**
     * 抽象方法,子类需要实现焦点变化事件处理逻辑。
     */
    abstract void focusChanged();

    /**
     * 抽象方法,子类需要实现鼠标按下事件处理逻辑。
     */
    abstract void mousePressed();
}
