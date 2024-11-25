package com.token.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring工具类,用于获取和管理Spring应用上下文。
 */
@Component
public class SpringUtils implements ApplicationContextAware {

	/**
	 * 存储Spring应用上下文实例。
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口中的setApplicationContext方法。
	 * 在Spring容器初始化bean后调用此方法设置应用上下文。
	 *
	 * @param applicationContext Spring应用上下文
	 * @throws BeansException 如果出现异常
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		if (SpringUtils.applicationContext == null) {
			SpringUtils.applicationContext = applicationContext;
		}
		// 如果applicationContext已经被设置,则不再重新赋值
	}

	/**
	 * 获取Spring应用上下文实例。
	 *
	 * @return Spring应用上下文实例
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 根据bean名称获取bean对象。
	 *
	 * @param name bean的名称
	 * @return bean对象
	 */
	public static Object getBean(String name) {
		return getApplicationContext().getBean(name);
	}

	/**
	 * 根据类类型获取bean对象。
	 *
	 * @param clazz 要查找的bean的类类型
	 * @return bean对象
	 */
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	/**
	 * 根据bean名称和类类型获取bean对象。
	 *
	 * @param name bean的名称
	 * @param clazz 要查找的bean的类类型
	 * @return bean对象
	 */
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}
}
