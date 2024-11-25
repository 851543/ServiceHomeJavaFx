package com.token.config;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.gn.decorator.GNDecorator;
import com.token.fx.SpringFXMLLoader;
import com.token.fx.StageManager;
import com.token.utils.ExceptionWriter;

import javafx.stage.Stage;

/**
 * Java配置类,用于配置应用程序的各种 bean。
 */
@Configuration
public class AppJavaConfig {

    /**
     * Spring FXML 加载器 bean。
     */
    @Autowired
    private SpringFXMLLoader springFXMLLoader;

    /**
     * 创建一个异常写入器 bean。
     *
     * @return 新创建的 ExceptionWriter 实例
     */
    @Bean
    @Scope("prototype")
    public ExceptionWriter exceptionWriter() {
        // 创建一个新的 ExceptionWriter 实例,使用 StringWriter 来存储异常信息
        return new ExceptionWriter(new StringWriter());
    }

    /**
     * 获取资源文件包。
     *
     * @return 资源文件包
     */
    @Bean
    public ResourceBundle resourceBundle() {
        // 加载名为 "Bundle" 的资源文件包
        return ResourceBundle.getBundle("Bundle");
    }

    /**
     * 创建 StageManager bean。
     *
     * @param stage JavaFX 窗口
     * @param decorator GNDecorator 实例
     * @return 新创建的 StageManager 实例
     * @throws IOException 可能抛出的 IO 异常
     */
    @Bean
    @Lazy
    public StageManager stageManager(Stage stage, GNDecorator decorator) throws IOException {
        // 使用 Spring FXML 加载器、窗口和装饰器创建新的 StageManager 实例
        return new StageManager(springFXMLLoader, stage, decorator);
    }

}
