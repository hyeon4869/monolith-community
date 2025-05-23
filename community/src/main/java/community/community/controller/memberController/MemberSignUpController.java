package community.community.controller.memberController;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.service.memberService.MemberSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberSignUpController {

    private final MemberSignUpService memberSignUpService;

    @PostMapping("/signUp")
    public ResponseEntity<Map<String, Object>> signUp(@RequestBody MemberDTO memberDTO) {
        Map<String, Object> response = new HashMap<>();
        String signUpId =  memberSignUpService.signUp(memberDTO);
        response.put("message","회원가입 완료");
        response.put("signUpId", signUpId);
        return ResponseEntity.ok(response);

    }
}
