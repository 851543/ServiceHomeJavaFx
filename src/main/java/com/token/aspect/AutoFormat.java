package com.token.aspect;

import com.token.entity.User;
import com.token.utils.ServiceHomeUtils;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class AutoFormat {

    private Logger log = LoggerFactory.getLogger(AutoFormat.class);

    @Pointcut("execution(* com.token.mapper.*.*(..)) && @annotation(com.token.annotation.AutoFormat)")
    public void autoFormatPointcut(){

    }

    @AfterReturning(pointcut = "autoFormatPointcut()", returning = "returnValue")
    public void autoFormatAfterReturning(Object returnValue) {
        log.info("获取切入返回数据:{}", returnValue);
        if (returnValue instanceof List){
            ((List<?>) returnValue).stream().forEach(item->{
                if (item instanceof User){
                    User user = (User) item;
                    user.setSex(ServiceHomeUtils.sexType(user.getSex()));
                    user.setStatus(ServiceHomeUtils.setStatusType(user.getStatus()));
                }
            });
        }
    }

    @AfterThrowing(pointcut = "autoFormatPointcut()", throwing = "ex")
    public void autoFormatExceptionHandling(Throwable ex) {
        log.error(ex.getMessage());
    }
}
