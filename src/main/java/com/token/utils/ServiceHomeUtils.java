package com.token.utils;

import com.token.entity.User;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.regex.Pattern;

public class ServiceHomeUtils {

    private static final Logger log = LoggerFactory.getLogger(ServiceHomeUtils.class);

    private static final String localPath = "D:\\token\\file";

    private static User user;

    public static String avatarImage(ImageView image) {
        log.info("文件本地存储:{}", image);
        File localFile = new File(localPath);
        // 判断当前服务器是否有该路径
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        BufferedImage bImage = SwingFXUtils.fromFXImage(image.getImage(), null);
        // 获取原文件名
        String originalFilename = image.getImage().impl_getUrl();
        // 获取文件后缀
        String extension = originalFilename.substring(originalFilename.lastIndexOf('.') + 1);
        // 重新构建文件名
        String fileName = UUID.randomUUID() + "." + extension;
        // 本地存放路径
        String fileLocalPath = localPath + "\\" + fileName;
        Runnable uploadTask  = ()->{
            try {
                ImageIO.write(bImage, extension, new File(fileLocalPath));
            } catch (IOException e) {
                log.error("文件存储失败:{}", fileLocalPath);
            }
        };
        new Thread(uploadTask).start();
        log.info("文件存储成功:{}", fileLocalPath);
        return fileLocalPath;
    }

    public static String setStatusType(String status) {
        return status.equals("0") ? "正常" : "禁用";
    }

    public static String sexType(String sex) {
        return sex.equals("0") ? "男" : "女";
    }

    public static String sexType(Object sex) {
        return sex.toString().equals("男") ? "0" : "1";
    }

    public static boolean phoneValidate(String phone) {
        return Pattern.matches("^1[3-9]\\d{9}$", phone);
    }

    public static String setStatusType(Object status) {
        return status.toString().equals("启用") ? "0" : "1";
    }

    public static void setLoginUserInfo(User loginUser){
        user = loginUser;
    }

    public static User getLoginUserInfo(){
        return user;
    }
}
