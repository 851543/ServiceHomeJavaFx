package com.token.utils;

import java.io.PrintWriter;
import java.io.Writer;

/**
 * 自定义异常处理类,用于将Throwable转换为字符串。
 */
public class ExceptionWriter extends PrintWriter {

	/**
	 * 构造函数,传入一个Writer对象作为输出目标。
	 *
	 * @param writer Writer对象,用于写入异常信息
	 */
	public ExceptionWriter(Writer writer) {
		super(writer);
	}

	/**
	 * 包装字符串,在两端添加换行符。
	 *
	 * @param stringWithoutNewlines 原始字符串
	 * @return 包装后的字符串
	 */
	private String wrapAroundWithNewlines(String stringWithoutNewlines) {
		return ("\n" + stringWithoutNewlines + "\n");
	}

	/**
	 * 将Throwable转换为字符串。
	 *
	 * @param throwable 需要转换的异常对象
	 * @return 转换后的异常信息字符串
	 */
	public String getExceptionAsString(Throwable throwable) {
		// 调用printStackTrace方法将异常信息打印到Writer中
		throwable.printStackTrace(this);

		// 获取打印后的输出内容
		String exception = super.out.toString();

		// 包装输出内容并返回
		return (wrapAroundWithNewlines(exception));
	}
}
