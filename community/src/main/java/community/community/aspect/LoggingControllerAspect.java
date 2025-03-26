package community.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 컨트롤러 메소드의 실행을 로깅하기 위한 AOP(Aspect-Oriented Programming) 클래스
 * 모든 컨트롤러 메소드의 실행 전, 실행 후, 예외 발생 시 로그를 기록함
 */
@Component // 스프링 빈으로 등록
@Aspect    // AOP 관점 클래스임을 명시
public class LoggingControllerAspect {

    // 로깅을 위한 Logger 객체 생성
    private final Logger logger = LoggerFactory.getLogger(LoggingControllerAspect.class);

    /**
     * 컨트롤러 패키지 내의 모든 메소드를 대상으로 하는 포인트컷 정의
     */
    @Pointcut("execution(* community.community.controller.*.*.*(..))")
    public void ControllerMethods(){}

    /**
     * 컨트롤러 메소드 실행 전에 메소드명과 인자를 로깅
     * @param joinPoint 대상 메소드 정보를 담고 있는 객체
     */
    @Before("ControllerMethods()")
    public void logBeforeMethods(JoinPoint joinPoint){
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();

        logger.info("[[Executing methodName: {} with argument: {}]]", methodName, Arrays.toString(methodArgs));
    }

    /**
     * 컨트롤러 메소드 정상 실행 후 메소드명과 반환값을 로깅
     * @param joinPoint 대상 메소드 정보를 담고 있는 객체
     * @param result 메소드의 반환값
     */
    @AfterReturning(value = "ControllerMethods()", returning = "result")
    public void logAfterMethods(JoinPoint joinPoint, Object result){
        String methodName = joinPoint.getSignature().getName();

        logger.info("[[Method: {}, Returned: {}]]", methodName, result);
    }

    /**
     * 컨트롤러 메소드 실행 중 예외 발생 시 메소드명과 에러 메시지를 로깅
     * @param joinPoint 대상 메소드 정보를 담고 있는 객체
     * @param error 발생한 예외 객체
     */
    @AfterThrowing(value = "ControllerMethods()", throwing = "error")
    public void logAfterError(JoinPoint joinPoint, Throwable error){
        String methodName = joinPoint.getSignature().getName();
        logger.error("[[Method: {}, threw an Exception: {}]]", methodName, error.getMessage());
    }
}
