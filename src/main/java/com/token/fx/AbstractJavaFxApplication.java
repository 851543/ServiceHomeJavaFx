package com.token.fx;

import static com.sun.javafx.application.LauncherImpl.launchApplication;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.token.eunms.FxmlView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.gn.decorator.GNDecorator;
import com.sun.javafx.application.LauncherImpl;

import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

/**
 * 抽象JavaFX应用程序类
 * @author 阿俊
 * @description
 */
public abstract class AbstractJavaFxApplication extends Application {

    // 日志记录器
    protected static Logger LOGGER = LoggerFactory.getLogger(AbstractJavaFxApplication.class);

    // Spring应用上下文
    protected static ConfigurableApplicationContext applicationContext;

    // 场景管理器
    protected StageManager stageManager;

    // 预加载的视图列表
    protected static List<FxmlView> preloadViews;

    // 初始化视图
    protected static FxmlView initView;

    // 进度条进度
    private float progress = 0;

    /**
     * 启动应用程序
     * @param appClass 应用类
     * @param _preloadViews 预加载的视图列表
     * @param _initView 初始化视图
     * @param args 命令行参数
     */
    public static void run(final Class<? extends Application> appClass, final List<FxmlView> _preloadViews,
                           final FxmlView _initView, final String[] args) {
        preloadViews = _preloadViews;
        initView = _initView;

        // 使用CompletableFuture异步启动Spring应用上下文
        CompletableFuture.supplyAsync(() -> applicationContext = SpringApplication.run(appClass, args))
                .whenComplete((ctx, throwable) -> {
                    if (throwable != null) {
                        // 如果启动失败,记录错误日志
                        LOGGER.error("Failed to load spring application context: ", throwable);
                    } else {
                        // 启动JavaFX应用程序
                        launchApplication(appClass, JavaFxAppPreloader.class, args);
                    }
                });
    }

    /**
     * 初始化方法
     */
    @Override
    public synchronized void init() {
        try {
            // 预加载所有预定义的视图
            for (FxmlView view : preloadViews) {
                // 加载FXML文件
                FXMLLoader.load(getClass().getResource(view.fxml()));
                notifyLoader(); // 通知加载器进度更新
            }
        } catch (IOException e) {
            // 如果加载失败,打印错误日志
            e.printStackTrace();
        }
    }

    /**
     * 通知加载器更新进度
     */
    private synchronized void notifyLoader() {
        // 更新进度(每100个视图增加1%)
        progress += 100f / preloadViews.size();
        try {
            // 等待1秒以显示加载进度
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // 忽略中断异常
        }
        // 通知JavaFX加载器更新进度
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }

    /**
     * 启动方法
     * @param primary 主舞台
     */
    @Override
    public void start(Stage primary) {
        // 创建GNDecorator实例
        GNDecorator decorator = new GNDecorator();

        // 获取Spring应用上下文中的StageManager bean
        stageManager = applicationContext.getBean(StageManager.class, primary, decorator);

        // 切换到初始化视图
        stageManager.switchScene(initView);

        // 显示装饰器
        stageManager.showDecorator();
    }

    /**
     * 停止方法
     * @throws Exception 可能的异常
     */
    @Override
    public void stop() throws Exception {
        super.stop();
        if (applicationContext != null) {
            // 关闭Spring应用上下文
            applicationContext.close();
        }
    }
}
