package community.community.service.memberService;

import community.community.exception.customException.NotFoundMemberException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class PasswordValidator {


    public void validate(String dtoPassword, String storePassword) {
        if(!dtoPassword.equals(storePassword)){
            throw new NotFoundMemberException("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.");
        }
    }
}