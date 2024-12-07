package com.token.eunms;

import java.util.ResourceBundle;

/**
 * 定义了应用程序中不同视图的枚举类。
 * 每个枚举常量代表一个特定的FXML视图。
 * @author 阿俊
 * @description
 */
public enum FxmlView {

    /**
     * 登录页面枚举常量
     */
    LOGIN {
        @Override
        public String title() {
            return getStringFromResourceBundle("login.title");
        }

        @Override
        public String fxml() {
            return "/template/login/login.fxml";
        }
    },

    /**
     * 主页面枚举常量
     */
    MAIN {
        @Override
        public String title() {
            return getStringFromResourceBundle("main.title");
        }

        @Override
        public String fxml() {
            return "/template/main/main.fxml";
        }
    },

    REPAIR{
        @Override
        public String title() {
            return getStringFromResourceBundle("repair.title");
        }

        @Override
        public String fxml() {
            return "/template/repair/repair.fxml";
        }
    },
//    SERVICE{
//        @Override
//        public String title() {
//            return getStringFromResourceBundle("service.title");
//        }
//
//        @Override
//        public String fxml() {
//            return "/template/service/service.fxml";
//        }
//    },
//    STUDENT{
//        @Override
//        public String title() {
//            return getStringFromResourceBundle("student.title");
//        }
//
//        @Override
//        public String fxml() {
//            return "/template/student/student.fxml";
//        }
//    },
    USER{
        @Override
        public String title() {
            return getStringFromResourceBundle("user.title");
        }

        @Override
        public String fxml() {
            return "/template/user/user.fxml";
        }
    };

    /**
     * 声明抽象方法title()和fxml()
     */
    public abstract String title();

    /**
     * 声明抽象方法fxml()
     */
    public abstract String fxml();

    /**
     * 从资源文件中获取字符串值的辅助方法
     * @param key 资源文件中的键
     * @return 对应的字符串值
     */
    private static String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}
