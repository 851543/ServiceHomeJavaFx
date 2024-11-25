package com.token.fx.skin;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * 自定义的Viewer皮肤类,继承自SkinAction。
 * 用于处理密码字段的文本遮蔽和显示功能。
 */
public class ViewerSkin extends SkinAction {

    // 是否应该遮蔽文本的标志
    private boolean shouldMaskText = true;

    // 构造函数
    /**
     * 构造方法,接受一个PasswordField作为参数。
     * @param textField PasswordField对象
     */
    public ViewerSkin(PasswordField textField) {
        super(textField);
    }

    // 重写鼠标释放事件处理方法
    /**
     * 当鼠标释放时调用此方法。
     * 将文本设置为可见并结束编辑。
     */
    @Override
    void mouseReleased() {
        TextField textField = getSkinnable();
        // 将文本设置为可见
        textField.setText(textField.getText());
        textField.end();
    }

    // 重写文本改变事件处理方法
    /**
     * 当文本发生变化时调用此方法。
     * 根据是否有焦点和文本是否为空来控制按钮的可见性。
     */
    @Override
    void textChanged() {
        // 如果密码字段不在焦点且文本为空则返回
        if (!getPasswordField().isFocused() && getPasswordField().getText() == null) {
            return;
        }
        // 显示按钮的可见性取决于是否有焦点且文本不为空
        getButton().setVisible(getPasswordField().isFocused() && !getPasswordField().getText().isEmpty());
    }

    // 重写焦点改变事件处理方法
    /**
     * 当焦点发生变化时调用此方法。
     * 与textChanged()方法类似,用于控制按钮的可见性。
     */
    @Override
    void focusChanged() {
        // 如果密码字段不在焦点且文本为空则返回
        if (!getPasswordField().isFocused() && getPasswordField().getText() == null) {
            return;
        }
        // 显示按钮的可见性取决于是否有焦点且文本不为空
        getButton().setVisible(getPasswordField().isFocused() && !getPasswordField().getText().isEmpty());
    }

    // 重写鼠标按下事件处理方法
    /**
     * 当鼠标按下时调用此方法。
     * 将shouldMaskText标志设置为false并显示文本。
     */
    @Override
    void mousePressed() {
        TextField textField = getSkinnable();
        // 设置shouldMaskText标志为false
        shouldMaskText = false;
        // 将文本设置为可见
        textField.setText(textField.getText());
        // 恢复shouldMaskText标志的值
        shouldMaskText = true;
    }

    // 重写maskText方法以遮蔽密码文本
    /**
     * 用于遮蔽密码文本。
     * 如果当前组件是密码字段且shouldMaskText为true,
     * 则返回一个由星号字符填充的字符串。
     * 否则返回原始文本。
     * @param txt 要遮蔽的文本
     * @return 遮蔽后的文本字符串
     */
    @Override
    protected String maskText(String txt) {
        // 如果当前组件是密码字段且shouldMaskText为true
        if (getSkinnable() instanceof PasswordField && shouldMaskText) {
            // 创建一个新的StringBuilder对象
            StringBuilder passwordBuilder = new StringBuilder(txt.length());
            // 遍历文本长度，填充星号字符
            for (int i = 0; i < txt.length(); i++) {
                passwordBuilder.append(BULLET); // BULLET是一个常量，通常定义为'*'字符
            }
            // 返回填充后的字符串
            return passwordBuilder.toString();
        } else {
            // 如果不需要遮蔽，返回原始文本
            return txt;
        }
    }

    // BULLET常量，通常定义为'*'字符
    private static final char BULLET = '\u2022';
}
