package community.community.exception.customException;

public class InvalidEmailException extends RuntimeException{

    public InvalidEmailException(String message){
        super(message);
    }
}
