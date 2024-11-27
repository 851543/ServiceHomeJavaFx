package com.token.fx;

import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Spring FXML加载器类,用于在Spring应用中加载和管理JavaFX的FXML文件。
 * @author 阿俊
 * @description
 */
@Component
public class SpringFXMLLoader {

	/**
	 * 资源包,用于加载本地化资源。
	 */
	private final ResourceBundle resourceBundle;

	/**
	 * Spring应用上下文,用于获取Spring管理的Bean。
	 */
	private final ApplicationContext context;

	/**
	 * 构造函数,注入ApplicationContext和ResourceBundle。
	 * @param context Spring应用上下文
	 * @param resourceBundle 资源包
	 */
	@Autowired
	public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
		this.resourceBundle = resourceBundle;
		this.context = context;
	}

	/**
	 * 加载指定路径的FXML文件并返回其根元素。
	 * @param fxmlPath FXML文件的相对路径
	 * @return 加载后的JavaFX Parent对象
	 * @throws IOException 如果在加载过程中发生IO异常
	 */
	public Parent load(String fxmlPath) throws IOException {
		// 创建新的FXMLLoader实例
		FXMLLoader loader = new FXMLLoader();

		// 设置控制器工厂,使用Spring应用上下文获取Bean
		loader.setControllerFactory(context::getBean);

		// 设置资源包,用于本地化
		loader.setResources(resourceBundle);

		// 设置FXML文件的位置
		loader.setLocation(getClass().getResource(fxmlPath));

		// 加载FXML文件并返回根元素
		return loader.load();
	}
}
