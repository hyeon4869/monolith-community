package community.community.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 서비스 계층의 메소드 실행을 로깅하기 위한 AOP(Aspect-Oriented Programming) 클래스
 * 비밀번호 검증 메소드를 제외한 모든 서비스 메소드의 실행 전, 실행 후, 예외 발생 시 로그를 기록함
 */
@Component // 스프링 빈으로 등록
@Aspect    // AOP 관점 클래스임을 명시
public class LoggingServiceAspect {

    // 로깅을 위한 Logger 객체 생성
    private final Logger logger = LoggerFactory.getLogger(LoggingServiceAspect.class);

    /**
     * 서비스 패키지 내의 모든 메소드를 대상으로 하는 포인트컷 정의
     */
    @Pointcut("execution(* community.community.service.*.*.*(..))")
    public void serviceMethods() {}

    /**
     * 비밀번호 검증 메소드를 대상으로 하는 포인트컷 정의
     */
    @Pointcut("execution(* community.community.service.memberService.PasswordValidator.*(..))")
    public void passwordValidatorMethods(){}

    /**
     * 비밀번호 검증 메소드를 제외한 서비스 메소드 실행 전에 메소드명과 인자를 로깅
     * @param joinPoint 대상 메소드 정보를 담고 있는 객체
     */
    @Before("serviceMethods() && !passwordValidatorMethods()")
    public void logBeforeMethods(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] methodArgs = joinPoint.getArgs();
        logger.info("[[Executing methodName: {} with arguments: {}]]", methodName, Arrays.toString(methodArgs));
    }

    /**
     * 서비스 메소드 정상 실행 후 메소드명과 반환값을 로깅
     * @param joinPoint 대상 메소드 정보를 담고 있는 객체
     * @param result 메소드의 반환값
     */
    @AfterReturning(pointcut = "serviceMethods()" , returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        logger.info("[[Method {} returned: {}]]", methodName, result);
    }

    /**
     * 서비스 메소드 실행 중 예외 발생 시 메소드명과 에러 메시지를 로깅
     * @param joinPoint 대상 메소드 정보를 담고 있는 객체
     * @param error 발생한 예외 객체
     */
    @AfterThrowing(pointcut = "serviceMethods()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        String methodName = joinPoint.getSignature().getName();
        logger.error("[[Method {} threw an exception: {}]]", methodName, error.getMessage());
    }
}
