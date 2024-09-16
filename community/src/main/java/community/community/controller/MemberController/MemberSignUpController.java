package community.community.controller.MemberController;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.interfaceService.memberInterface.MemberSignUpInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberSignUpController {

    private final MemberSignUpInterface memberSignUpInterface;

    //기본 회원가입 로직
    public MemberSignUpController(@Qualifier("basicSignUp") MemberSignUpInterface memberSignUp){
        this.memberSignUpInterface = memberSignUp;
    }
    @GetMapping("/")
    public String Hello(){
        String hello = memberSignUpInterface.hello();
        return hello;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@ModelAttribute  MemberDTO memberDTO) {
        Map<String, Object> response = new HashMap<>();
        String signUpId =  memberSignUpInterface.signUp(memberDTO);

        response.put("message","회원가입 완료");
        response.put("signUpId", signUpId);
        return ResponseEntity.ok(response);

    }
}
