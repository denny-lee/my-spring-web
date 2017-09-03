package com.lee.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Field;

//@Aspect
//@Component
//@Configuration
//@EnableAspectJAutoProxy
public class TimerAspect {
    private static final Logger logger = LoggerFactory.getLogger(TimerAspect.class);

//    @Pointcut("execution(* com.lee.controller.*.*(..))")
    public void pt() {}
//    @Before("pt()")
    public void logTimeCost() {
        logger.info("-----------logTimeCost--------");
    }
}
