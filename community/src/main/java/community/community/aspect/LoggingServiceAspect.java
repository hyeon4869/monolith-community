package community.community.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class LoggingServiceAspect {

    private final Logger logger = LoggerFactory.getLogger(LoggingServiceAspect.class);

    // 공통 포인트컷 정의
    @Pointcut("execution(* community.community.service.*.*.*(..))")
    public void serviceMethods() {}

    @Pointcut("execution(* community.community.service.memberService.PasswordValidator.*(..))")
    public void passwordValidatorMethods(){}

    @Before("serviceMethods() && !passwordValidatorMethods()")
    public void logBeforeMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        logger.info("[[Executing methodName: {} with arguments: {}]]", methodName, Arrays.toString(methodArgs));
    }

    @AfterReturning(pointcut = "serviceMethods()" , returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("[[Method {} returned: {}]]", methodName, result);
    }

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("[[Method {} threw an exception: {}]]", methodName, error.getMessage());
    }

}
