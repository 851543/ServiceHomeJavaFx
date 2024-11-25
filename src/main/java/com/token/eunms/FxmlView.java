package com.token.eunms;

import java.util.ResourceBundle;

public enum FxmlView {
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
    MAIN {
        @Override
        public String title() {
            return getStringFromResourceBundle("main.title");
        }

        @Override
        public String fxml() {
            return "/template/main/main.fxml";
        }

    };


    public abstract String title();
    public abstract String fxml();

    String getStringFromResourceBundle(String key){
        return ResourceBundle.getBundle("Bundle").getString(key);
    }

}