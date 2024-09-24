package community.community.service.memberService;

import community.community.exception.customException.NotFoundMemberException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class PasswordValidator {


    public void validate(String dtoPassword, String storePassword, String errorMassage) {
        if(!dtoPassword.equals(storePassword)){
            throw new NotFoundMemberException(errorMassage);
        }
    }
}
