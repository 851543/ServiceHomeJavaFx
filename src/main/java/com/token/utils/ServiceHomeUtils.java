package com.token.utils;

import com.token.entity.User;
import com.token.eunms.FxmlView;
import com.token.eunms.UserRole;
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
import java.util.stream.Collectors;

public class ServiceHomeUtils {

    private static final Logger log = LoggerFactory.getLogger(ServiceHomeUtils.class);

    private static final String localPath = "D:\\token\\file";

    private static User user;

    private static UserRole loginUserRole;

    private static UserRole changeRole;

    public static String defaultImagePath = "E:\\idea\\project\\Java\\ServiceHomeJavaFx\\src\\main\\resources\\styles\\img\\pe.png";

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

    public static String setRepairStatusType(String status) {
        if (status.equals("0" )){
            return "未提交";
        }
        if (status.equals("1" )){
            return "待受理";
        }
        if (status.equals("2")){
            return  "已派工";
        }
        if (status.equals("3")){
            return "维修完成";
        }
        return "";
    }

    public static String setRepairStatusType(Object status) {
        if (status.equals("未提交") || status.equals("保存")){
            return "0";
        }
        if (status.equals("待受理") || status.equals("提交")){
            return "1";
        }
        if (status.equals("已派工")){
            return "2";
        }
        if (status.equals("维修完成")){
            return "3";
        }
        return "";
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

    public static UserRole getChangeRole() {
        return changeRole;
    }

    public static void setChangeRole(UserRole changeRole) {
        ServiceHomeUtils.changeRole = changeRole;
    }

    public static UserRole getLoginUserRole() {
        return loginUserRole;
    }

    public static void setLoginUserRole(UserRole loginUserRole) {
        ServiceHomeUtils.loginUserRole = loginUserRole;
    }

    public static String observableListToString(ObservableList<String> items) {
        return items.stream().collect(Collectors.joining(","));
    }
}
