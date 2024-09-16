package community.community.exception.customException;

public class NotFoundMemberException extends RuntimeException{
    public NotFoundMemberException(String message){
        super(message);
    }
}
