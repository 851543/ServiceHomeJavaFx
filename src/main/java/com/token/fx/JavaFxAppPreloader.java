package com.token.fx;

import java.io.IOException;

import com.jfoenix.controls.JFXProgressBar;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * 自定义JavaFX预加载器类
 */
public class JavaFxAppPreloader extends Preloader {

	// 进度条组件
	private JFXProgressBar progressBar;

	// 视图组件
	private Parent view;

	// 窗口组件
	private Stage stage;

	// 标签组件
	private Label label;

	/**
	 * 初始化方法
	 */
	@Override
	public void init() {
		try {
			// 加载加载器视图
			view = FXMLLoader.load(getClass().getResource("/template/loader/loader.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动方法
	 * @param primary 主窗口
	 */
	@Override
	public void start(Stage primary) {
		stage = primary;

		// 设置窗口样式为透明
		primary.initStyle(StageStyle.TRANSPARENT);

		// 创建场景
		Scene scene = new Scene(view);
		scene.setFill(Color.TRANSPARENT);  // 设置场景背景为透明

		// 添加样式表
		scene.getStylesheets().add(getClass().getResource("/styles/css/fonts.css").toExternalForm());

		// 查找并设置进度条和标签组件
		progressBar = (JFXProgressBar) scene.lookup("#progressBar");
		label = (Label) scene.lookup("#progressLabel");

		// 设置窗口图标
		primary.getIcons().add(new Image("/styles/img/icon.png"));

		// 设置场景并显示窗口
		primary.setScene(scene);
		primary.setAlwaysOnTop(true);  // 设置窗口始终在顶层
		primary.show();
	}

	/**
	 * 处理应用程序通知方法
	 * @param info 通知信息
	 */
	@Override
	public synchronized void handleApplicationNotification(PreloaderNotification info) {
		if (info instanceof ProgressNotification) {
			// 获取进度值
			double x = ((ProgressNotification) info).getProgress();

			// 计算百分比并设置进度条值
			double percent = x / 100f;
			progressBar.progressProperty().set(percent > 1 ? 1 : percent);
		}
	}

	/**
	 * 处理状态变化通知方法
	 * @param info 状态变化通知
	 */
	@Override
	public void handleStateChangeNotification(StateChangeNotification info) {
		try {
			// 获取状态类型
			StateChangeNotification.Type type = info.getType();

			switch (type) {
				case BEFORE_LOAD:
					// 在加载前状态改变时执行
					label.textProperty().set("初始化成功...");
					Thread.sleep(2000);  // 等待2秒
					break;

				case BEFORE_INIT:
					// 在初始化前状态改变时执行
					label.textProperty().set("正在加载模块...");
					Thread.sleep(1000);  // 等待1秒
					break;

				case BEFORE_START:
					// 在启动前状态改变时执行
					label.textProperty().set("加载成功，即将跳转到主页面");
					Thread.sleep(1000);  // 等待1秒

					// 关闭加载器窗口
					stage.close();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
