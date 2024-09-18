package community.community.controller.MemberController;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.entity.Member;
import community.community.interfaceService.memberInterface.MemberLoginInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberLoginController {

    private final MemberLoginInterface memberLoginInterface;

    public MemberLoginController(@Qualifier("basicLogin") MemberLoginInterface memberLoginInterface){
        this.memberLoginInterface=memberLoginInterface;
    }

    @PostMapping("/memberLogin")
    public ResponseEntity<Map<String, Object>> memberLogin(@RequestBody MemberLoginDTO memberLoginDTO){
        Map<String, Object> response = new HashMap<>();
        Member member=memberLoginInterface.memberLogin(memberLoginDTO);
        response.put("message", "로그인 완료");
        return ResponseEntity.ok(response);
    }
}
