package com.token.aspect;

import com.token.annotation.AutoFill;
import com.token.constant.AutoFillConstant;
import com.token.eunms.OperationType;
import com.token.utils.ServiceHomeUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class AutoFillAspect {

    protected Logger log = LoggerFactory.getLogger(AutoFillAspect.class);

    @Pointcut("execution(* com.token.mapper.*.*(..)) && @annotation(com.token.annotation.AutoFill))")
    public void autoFillPointcut() {
    }


    @Before("autoFillPointcut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("公共字段填充开始:{}", joinPoint);

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = methodSignature.getMethod().getAnnotation(AutoFill.class);
        OperationType operationType = annotation.value();

        Object[] args = joinPoint.getArgs();
        if (ObjectUtils.isEmpty(args) && args.length == 0) {
            log.info("参数错误:{}", args);
            return;
        }

        Object entity = args[0];

        LocalDateTime now = LocalDateTime.now();
        String id = ServiceHomeUtils.getLoginUserInfo().getId().toString();

        try {
            Method DEL_FLAG = entity.getClass().getDeclaredMethod(AutoFillConstant.DEL_FLAG, String.class);
            DEL_FLAG.invoke(entity,"0");
            if (operationType == OperationType.INSERT) {
                Method CREATE_BY = entity.getClass().getDeclaredMethod(AutoFillConstant.CREATE_BY, String.class);
                Method CREATE_TIME = entity.getClass().getDeclaredMethod(AutoFillConstant.CREATE_TIME, LocalDateTime.class);
                Method UPDATE_BY = entity.getClass().getDeclaredMethod(AutoFillConstant.UPDATE_BY, String.class);
                Method UPDATE_TIME = entity.getClass().getDeclaredMethod(AutoFillConstant.UPDATE_TIME, LocalDateTime.class);

                CREATE_BY.invoke(entity,id);
                CREATE_TIME.invoke(entity,now);
                UPDATE_BY.invoke(entity,id);
                UPDATE_TIME.invoke(entity,now);
            }

            if (operationType == OperationType.UPDATE){
                Method UPDATE_BY = entity.getClass().getDeclaredMethod(AutoFillConstant.UPDATE_BY, String.class);
                Method UPDATE_TIME = entity.getClass().getDeclaredMethod(AutoFillConstant.UPDATE_TIME, LocalDateTime.class);

                UPDATE_BY.invoke(entity,id);
                UPDATE_TIME.invoke(entity,now);
            }
        } catch (Exception e) {
            log.error("反射处理数据失败:{}", e);
        }
    }
}
