package community.community.exception.customException;

public class SignUpFailedException extends RuntimeException{

    public SignUpFailedException(String message, Throwable cause){
        super(message, cause);
    }
}
