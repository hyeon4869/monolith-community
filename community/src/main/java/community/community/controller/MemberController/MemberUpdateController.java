package community.community.controller.MemberController;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.interfaceService.memberInterface.MemberUpdateInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberUpdateController {

    private final MemberUpdateInterface memberUpdateInterface;

    public MemberUpdateController(@Qualifier("basicUpdate") MemberUpdateInterface memberUpdateInterface){
        this.memberUpdateInterface=memberUpdateInterface;
    }

    @GetMapping("/findMember")
    public ResponseEntity<Map<String, Object>> findMember(@RequestParam String email){
        Map<String, Object> response = new HashMap<>();

         MemberDTO memberDTO = memberUpdateInterface.findMember(email);

        response.put("message", "회원조회를 합니다");
        response.put("email", memberDTO.getEmail());

        return ResponseEntity.ok(response);
    }

//    @PutMapping("/updateMember")
//    public ResponseEntity<Map<String, Object>> updateMember(@RequestParam String password){
//        Map<String, Object> response = new HashMap<>();
//
//        memberUpdateInterface.updatePassword(password);
//        return ResponseEntity.ok(response);
//    }

}
