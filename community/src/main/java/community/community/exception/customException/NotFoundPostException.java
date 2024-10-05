package community.community.exception.customException;

public class NotFoundPostException extends RuntimeException{
    public NotFoundPostException(String message, Throwable cause) {
        super(message, cause); // 메시지와 원인 예외를 부모 클래스에 전달
    }
}
