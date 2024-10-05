package community.community.exception.customException;

public class DBAccessException extends RuntimeException{

    public DBAccessException (String message, Throwable cause){
        super(message, cause);
    }
}
