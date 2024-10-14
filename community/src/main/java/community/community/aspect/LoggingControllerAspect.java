package community.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingControllerAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingControllerAspect.class);

    @Pointcut("execution(* community.community.controller.*.*.*(..))")
    public void ControllerAspect(){}

    @Before("ControllerAspect()")
    public void LoggingBeforeMethods(JoinPoint joinPoint){
           String methodName = joinPoint.getSignature().getName();
           Object[] args = joinPoint.getArgs();

           logger.info("Executing methodName: {} with argument: {}", methodName, args);
    }

    @AfterReturning("ControllerAspect()")
    public void LoggingAfterMethods(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();

        logger.info("Method: {}, Returned: {}", methodName, result);
    }

    @AfterThrowing("ControllerAspect()")
    public void LoggingAfterError(JoinPoint joinPoint, Throwable error){
        String methodName = joinPoint.getSignature().getName();

        logger.error("Method: {}, threw an Exception: {}", methodName, error.getMessage());
    }
}
