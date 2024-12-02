package com.token.fx;

import static org.slf4j.LoggerFactory.getLogger;

import java.util.Objects;

import com.token.eunms.FxmlView;
import javafx.scene.control.ScrollPane;
import org.slf4j.Logger;

import com.gn.decorator.GNDecorator;
import com.gn.decorator.options.ButtonType;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;

/**
 * StageManager 类用于管理 JavaFX 应用的窗口和场景切换。
 * 它包含了初始化窗口、加载场景和切换场景等功能。
 * @author 阿俊
 * @description
 */
public class StageManager {

	/**
	 * 日志记录器
	 */
	private static final Logger LOG = getLogger(StageManager.class);

	/**
	 * 主窗口
	 */
	private final Stage primaryStage;

	/**
	 * 用于加载 FXML 的类
	 */
	private final SpringFXMLLoader springFXMLLoader;

	/**
	 * 装饰器对象
	 */
	private final GNDecorator decorator;

	/**
	 * 构造函数
	 * @param springFXMLLoader 用于加载 FXML 的类
	 * @param stage 主窗口
	 * @param decorator 装饰器对象
	 */
	public StageManager(SpringFXMLLoader springFXMLLoader, Stage stage, GNDecorator decorator) {
		this.springFXMLLoader = springFXMLLoader;
		this.primaryStage = stage;
		this.decorator = decorator;

		// 设置窗口标题
		decorator.setTitle("维修之家");

		// 添加全效果按钮
		decorator.addButton(ButtonType.FULL_EFFECT);

		// 初始化主题为默认主题
		decorator.initTheme(GNDecorator.Theme.DEFAULT);

		// 设置关闭请求时的操作
		decorator.getStage().setOnCloseRequest(event -> {
			Platform.exit();
		});

		// 加载样式表
		decorator.getScene().getStylesheets().addAll(
				getClass().getResource("/styles/css/fonts.css").toExternalForm(),
				getClass().getResource("/styles/css/material-color.css").toExternalForm(),
				getClass().getResource("/styles/css/skeleton.css").toExternalForm(),
				getClass().getResource("/styles/css/light.css").toExternalForm(),
				getClass().getResource("/styles/css/bootstrap.css").toExternalForm(),
				getClass().getResource("/styles/css/shape.css").toExternalForm(),
				getClass().getResource("/styles/css/typographic.css").toExternalForm(),
				getClass().getResource("/styles/css/helpers.css").toExternalForm(),
				getClass().getResource("/styles/css/master.css").toExternalForm()
		);

		// 设置窗口为最大化状态
		decorator.setMaximized(true);

		// 设置窗口图标
		decorator.getStage().getIcons().add(new Image("/styles/img/icon.png"));
	}

	/**
	 * 切换到指定的场景
	 * @param view 指定的 FXML 视图
	 */
	public void switchScene(final FxmlView view) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.fxml());
		decorator.setContent(viewRootNodeHierarchy);
	}

	/**
	 * 局部切换到指定的场景
	 * @param view
	 * @param body
	 */
	public void switchContent(final FxmlView view, ScrollPane body) {
		Parent viewRootNodeHierarchy = loadViewNodeHierarchy(view.fxml());
		body.setContent(viewRootNodeHierarchy);
	}

	/**
	 * 加载视图节点层次结构
	 * @param fxmlFilePath FXML 文件路径
	 * @return 加载后的根节点
	 */
	private Parent loadViewNodeHierarchy(String fxmlFilePath) {
		Parent rootNode = null;
		try {
			rootNode = springFXMLLoader.load(fxmlFilePath);
			Objects.requireNonNull(rootNode, "A Root FXML node must not be null");
		} catch (Exception exception) {
			logAndExit("Unable to load FXML view" + fxmlFilePath, exception);
		}
		return rootNode;
	}

	/**
	 * 记录错误并退出应用程序
	 * @param errorMsg 错误消息
	 * @param exception 异常对象
	 */
	private void logAndExit(String errorMsg, Exception exception) {
		LOG.error(errorMsg, exception, exception.getCause());
		Platform.exit();
	}

	/**
	 * 显示装饰器
	 */
	public void showDecorator() {
		decorator.show();
	}
}
