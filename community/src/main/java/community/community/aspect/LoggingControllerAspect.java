package community.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingControllerAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingControllerAspect.class);

    @Pointcut("execution(* community.community.controller.*.*.*(..))")
    public void ControllerMethods(){}

    @Before("ControllerMethods()")
    public void logBeforeMethods(JoinPoint joinPoint){
           String methodName = joinPoint.getSignature().getName();
           Object[] methodArgs = joinPoint.getArgs();

           logger.info("[[Executing methodName: {} with argument: {}]]", methodName, Arrays.toString(methodArgs));
    }

    @AfterReturning(value = "ControllerMethods()", returning = "result")
    public void logAfterMethods(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();

        logger.info("[[Method: {}, Returned: {}]]", methodName, result);
    }

    @AfterThrowing(value = "ControllerMethods()", throwing = "error")
    public void logAfterError(JoinPoint joinPoint, Throwable error){
        String methodName = joinPoint.getSignature().getName();
        logger.error("[[Method: {}, threw an Exception: {}]]", methodName, error.getMessage());
    }

}
