package kosa.com.suntofu.L_LIFE.common.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
//클래스를 스프링 빈으로 등록
@Component
public class TimeTraceAop {

    // @Around("execution(* kosa.com..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {


        long start = System.currentTimeMillis();
        System.out.println("Start: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("End: " + joinPoint.toString() + " " + timeMs + "ms");
        }
    }
}