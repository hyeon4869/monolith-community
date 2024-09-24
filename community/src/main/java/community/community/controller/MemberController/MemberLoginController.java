package community.community.controller.MemberController;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.entity.Member;
import community.community.exception.customException.NotFoundMemberException;
import community.community.interfaceService.memberInterface.MemberLoginInterface;
import jakarta.servlet.http.HttpSession;
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
    public ResponseEntity<Map<String, Object>> memberLogin(@RequestBody MemberLoginDTO memberLoginDTO, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        Member member=memberLoginInterface.memberLogin(memberLoginDTO);
        session.setAttribute("loginEmail", member.getEmail());
        String loginEmail = (String) session.getAttribute("loginEmail");
        response.put("message", "로그인 완료");
        response.put("loginEmail", loginEmail);
        return ResponseEntity.ok(response);
    }

    
    @PostMapping("/memberLogout")
    public ResponseEntity<Map<String, Object>> memberLogout(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        String loginEmail = (String) session.getAttribute("loginEmail");
        if(loginEmail==null){
            //예외를 변경할 것
            throw new NotFoundMemberException("이미 로그아웃 상태입니다.");
        }
        session.invalidate();

        response.put("message","로그아웃하셨습니다.");
        return ResponseEntity.ok(response);
    }
}
