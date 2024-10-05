package community.community.controller.MemberController;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.service.memberService.MemberSignUpService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberSignUpController {

    private final MemberSignUpService memberSignUpService;

    //기본 회원가입 로직
    public MemberSignUpController(@Qualifier("basicSignUp") MemberSignUpService memberSignUp){
        this.memberSignUpService = memberSignUp;
    }
    @GetMapping("/")
    public String Hello(){
        String hello = memberSignUpService.hello();
        return hello;
    }

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody MemberDTO memberDTO) {
        System.out.println("memberDTO Email= " + memberDTO.getEmail());
        System.out.println("memberDTO Password= " + memberDTO.getPassword());
        Map<String, Object> response = new HashMap<>();
        String signUpId =  memberSignUpService.signUp(memberDTO);
        response.put("message","회원가입 완료");
        response.put("signUpId", signUpId);
        return ResponseEntity.ok(response);

    }
}
