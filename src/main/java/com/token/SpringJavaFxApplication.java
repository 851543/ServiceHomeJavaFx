package com.token;

import com.token.fx.AbstractJavaFxApplication;
import com.token.eunms.FxmlView;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Arrays;

@SpringBootApplication
public class SpringJavaFxApplication extends AbstractJavaFxApplication {

    public static void main(String[] args) {
        run(SpringJavaFxApplication.class,
                Arrays.asList(new FxmlView[]{FxmlView.MAIN, FxmlView.LOGIN,FxmlView.REPAIR,FxmlView.USER,FxmlView.DISPATCH}),
                FxmlView.LOGIN, args);
    }
}
