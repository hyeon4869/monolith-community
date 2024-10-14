package community.community.aspect;

import community.community.dto.MemberDTO.MemberDTO;
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

    @Pointcut("execution(* community.community.controller.MemberController.MemberSignUpController.signUp(..))")
    public void signUpMethods(){}

    @Before("ControllerMethods() && !signUpMethods()")
    public void logBeforeMethods(JoinPoint joinPoint){
           String methodName = joinPoint.getSignature().getName();
           Object[] methodArgs = joinPoint.getArgs();

           logger.info("[[Executing methodName: {} with argument: {}]]", methodName, Arrays.toString(methodArgs));
    }

    @AfterReturning("ControllerMethods()")
    public void logAfterMethods(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();

        logger.info("[[Method: {}, Returned: {}]]", methodName, result);
    }

    @AfterThrowing("ControllerMethods()")
    public void logAfterError(JoinPoint joinPoint, Throwable error){
        String methodName = joinPoint.getSignature().getName();
        logger.error("[[Method: {}, threw an Exception: {}]]", methodName, error.getMessage());
    }

    @Before("signUpMethods()")
    public void logBeforeSignUpMethod(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        if (methodArgs[0] instanceof MemberDTO memberDTO) {
            logger.info("[[Executing methodName: {} with email: {} with password: [[FILTERED]]]]", methodName, memberDTO.getEmail());
        }
    }

    @AfterReturning("signUpMethods()")
    public void logAfterSignUpMethods(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();

        logger.info("[[Method: {}]]", methodName);
    }
}
