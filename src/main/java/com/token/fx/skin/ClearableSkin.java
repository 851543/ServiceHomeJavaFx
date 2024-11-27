package com.token.fx.skin;

import javafx.scene.Cursor;
import javafx.scene.control.TextField;

/**
 * 清除按钮皮肤类。
 * 这个类扩展了SkinAction类,用于给TextField添加清除按钮功能。
 * @author 阿俊
 * @description
 */
public class ClearableSkin extends SkinAction {

    /**
     * 构造函数,初始化ClearableSkin对象。
     * @param textField TextField控件,用于清除文本的输入框。
     */
    public ClearableSkin(TextField textField) {
        super(textField);
    }

    /**
     * 重写鼠标释放事件处理函数。
     * 当鼠标释放时,清空输入框的内容。
     */
    @Override
    void mouseReleased() {
        // 清空输入框的文本内容
        getTextField().setText(""); // no null pointer
    }

    /**
     * 重写文本改变事件处理函数。
     * 当输入框的文本发生变化时,更新清除按钮的可见性。
     */
    @Override
    void textChanged() {
        // 如果输入框不处于焦点或文本为空,则返回
        if(!getTextField().isFocused() && getTextField().getText() == null )
            return;

        // 更新清除按钮的可见性
        // 如果输入框有焦点且文本不为空,则显示清除按钮
        getButton().setVisible(getTextField().isFocused() && !getTextField().getText().isEmpty());
    }

    /**
     * 重写焦点改变事件处理函数。
     * 当输入框的焦点状态发生变化时,更新清除按钮的可见性。
     */
    @Override
    void focusChanged() {
        // 如果输入框不处于焦点或文本为空,则返回
        if(!getTextField().isFocused() && getTextField().getText() == null )
            return;

        // 更新清除按钮的可见性
        // 如果输入框有焦点且文本不为空,则显示清除按钮
        getButton().setVisible(getTextField().isFocused() && !getTextField().getText().isEmpty());
    }

    /**
     * 设置鼠标光标的样式。
     * 当清除按钮可见时,设置为手型光标;否则恢复默认光标。
     */
    private void altCursor(){
        // 根据清除按钮的可见性设置不同的光标样式
        getButton().setCursor(
                getButton().isVisible()
                        ? Cursor.HAND : Cursor.DEFAULT
        );
    }

    /**
     * 重写鼠标按下事件处理函数。
     * 目前没有实现任何功能。
     */
    @Override
    void mousePressed() {
    }
}
