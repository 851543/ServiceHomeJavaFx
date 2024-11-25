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

@Configuration
public class AppJavaConfig {
	
    @Autowired 
    SpringFXMLLoader springFXMLLoader;

    @Bean
    @Scope("prototype")
    public ExceptionWriter exceptionWriter() {
        return new ExceptionWriter(new StringWriter());
    }

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }
    
    @Bean
    @Lazy
    public StageManager stageManager(Stage stage, GNDecorator decorator) throws IOException {
        return new StageManager(springFXMLLoader, stage, decorator);
    }
    
}
