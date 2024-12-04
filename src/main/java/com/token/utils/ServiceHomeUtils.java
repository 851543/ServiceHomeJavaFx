package com.token.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

public class ServiceHomeUtils {

    protected static final Logger log = LoggerFactory.getLogger(ServiceHomeUtils.class);

    public static String sexType(Object sex) {
        return sex.toString().equals("男") ? "0" : "1";
    }

    public static boolean phoneValidate(String phone) {
        return Pattern.matches("^1[3-9]\\d{9}$", phone);
    }

    public static String statusType(Object status) {
        return status.toString().equals("启用") ? "0" : "1";
    }

    public static String avatarImage(ImageView image, String localPath) {
        log.info("文件本地存储:{}", image);
        File localFile = new File(localPath);
        // 判断当前服务器是否有该路径
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        //  获取原文件名
        BufferedImage bImage = SwingFXUtils.fromFXImage(image.getImage(), null);
        String originalFilename = image.getUserData().toString();
        //  获取文件后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        //  重新构建文件名
        String fileName = UUID.randomUUID() + extension;
        try {
            // 将图像保存到输出文件
            ImageIO.write(bImage, fileName, new File(localFile + "\\" + fileName));
            return localFile + "\\" + fileName + fileName;
        } catch (IOException e) {
            log.error("文件上传失败：{}", e);
        }
        return "";
    }
}
