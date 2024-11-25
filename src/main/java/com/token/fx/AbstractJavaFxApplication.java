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


public abstract class AbstractJavaFxApplication extends Application {

    protected static Logger LOGGER = LoggerFactory.getLogger(AbstractJavaFxApplication.class);

    protected static ConfigurableApplicationContext applicationContext;

    protected StageManager stageManager;

    protected static List<FxmlView> preloadViews;
    protected static FxmlView initView;

    private float progress = 0;

    public static void run(final Class<? extends Application> appClass, final List<FxmlView> _preloadViews,
                           final FxmlView _initView, final String[] args) {
        preloadViews = _preloadViews;
        initView = _initView;

        CompletableFuture.supplyAsync(() -> applicationContext = SpringApplication.run(appClass, args))
                .whenComplete((ctx, throwable) -> {
                    if (throwable != null) {
                        LOGGER.error("Failed to load spring application context: ", throwable);
                    } else {
                        launchApplication(appClass, JavaFxAppPreloader.class, args);
                    }
                });
    }

    @Override
    public synchronized void init() {
        try {
            for (FxmlView view : preloadViews) {
                FXMLLoader.load(getClass().getResource(view.fxml()));
                notifyLoader();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void notifyLoader() {
        progress += 100f / preloadViews.size();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }

    @Override
    public void start(Stage primary) {
        GNDecorator decorator = new GNDecorator();
        stageManager = applicationContext.getBean(StageManager.class, primary, decorator);
        stageManager.switchScene(initView);
        stageManager.showDecorator();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (applicationContext != null) {
            applicationContext.close();
        }
    }

}
