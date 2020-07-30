package com.ssm_weixin.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class DemoAop {

    private static Logger logger = LoggerFactory.getLogger(DemoAop.class);

    private Date startTime;
    private Class executionClass;
    private Method executionMethod;

    @Before("execution(* com.ssm_weixin.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) {
        startTime = new Date();
        executionClass = joinPoint.getTarget().getClass();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        if(args==null||args.length==0){
            try {
                executionMethod = executionClass.getMethod(methodName);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }else {
            Class[] classArgs = new Class[args.length];
            for(int i=0;i<args.length;i++){
                classArgs[i] = args[i].getClass();
            }
            try {
                executionMethod = executionClass.getMethod(methodName,classArgs);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        logger.info(String.valueOf(executionMethod));
    }

    @After("execution(* com.ssm_weixin.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint){
        logger.info("后置");
    }
}
