package com.yuhui.store.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * AOP，统计业务方法执行时长
 */
@Aspect
@Component
public class TimerAspect {

    @Around("execution(* com.yuhui.store.service.impl.*.*(..))")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();//起始时间

        // 执行连接点方法，即切面所在位置对应的方法。本项目中表示执行注册或执行登录等
        Object result = pjp.proceed();

        long end = System.currentTimeMillis();//结束时间

        //计算耗时
        System.err.println("耗时：" + (end - start) + "ms.");

        return result;
    }
}
