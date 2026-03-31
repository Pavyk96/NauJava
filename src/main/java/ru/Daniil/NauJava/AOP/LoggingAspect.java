package ru.Daniil.NauJava.AOP;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * LoggingAspect
 *
 * @author Daniil Mezev
 */
@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(ru.Daniil.NauJava.AOP.Logger)")
    public Object logMethodArguments(ProceedingJoinPoint pjp) throws  Throwable {
        String methodName = pjp.getSignature().getName();
        Object[] args = pjp.getArgs();
        System.out.println("Method " + methodName + " called with arguments: " + Arrays.toString(args));
        Object result = pjp.proceed();
        System.out.println("Method " + methodName + " returned: " + result);
        return result;
    }
}
