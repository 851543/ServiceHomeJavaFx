package com.token.fx.component;

import javafx.scene.control.TextArea;
import javafx.scene.text.*;

/**
 * 可编辑的文本流组件，继承自JavaFX的TextArea。
 * 这个组件允许用户直接编辑文本，同时支持基本的富文本功能。
 */
public class EditableTextFlow extends TextArea {

    /**
     * 文本流对象，用于存储和显示文本节点。
     */
    private TextFlow textFlow;

    /**
     * 构造函数，初始化组件并设置初始文本。
     */
    public EditableTextFlow() {
        super();
        setEditable(true); // 设置组件为可编辑状态
        setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 12)); // 设置默认字体

        // 创建一个TextFlow对象作为内部容器
        textFlow = new TextFlow();
        getChildren().add(textFlow);

        // 添加初始文本到TextFlow中
        textFlow.getChildren().addAll(
                new Text("这是"),
                new Text("一个"),
                new Text("可编辑的"),
                new Text("文本流组件")
        );
    }

    /**
     * 向文本流添加样式化文本。
     * @param text 要添加的样式化文本字符串。
     */
    public void appendStyledText(String text) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            char c = text.charAt(i);
            if (c == '*') {
                if (i + 1 < text.length() && text.charAt(i+1) == '*') {
                    // 加粗文本
                    sb.append(new Text(""));
                    i += 2;
                } else if (i + 1 < text.length() && text.charAt(i+1) == '_') {
                    // 下划线文本
                    sb.append(new Text(""));
                    i += 2;
                }
            } else {
                sb.append(c);
            }
            i++;
        }

        // 清空现有内容
        textFlow.getChildren().clear();

        // 将字符串分割为单个字符数组
        String[] chars = sb.toString().split("");

        // 为每个字符创建一个新的Text节点并添加到TextFlow中
        for (String charStr : chars) {
            textFlow.getChildren().add(new Text(charStr));
        }
    }

    /**
     * 获取当前文本内容。
     * @return 文本内容字符串。
     */
    public String getTextContent() {
        StringBuilder content = new StringBuilder();
        TextFlow flow = (TextFlow) getChildren().get(0);
        flow.getChildren().forEach(node -> {
            if (node instanceof Text) {
                content.append(((Text) node).getText());
            }
        });
        return content.toString();
    }
}
