package community.community.controller.memberController;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.dto.MemberDTO.MemberSuccessLoginDTO;
import community.community.exception.customException.NotFoundMemberException;
import community.community.service.memberService.MemberLoginService;
import jakarta.servlet.http.HttpServletRequest;
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

    private final MemberLoginService memberLoginService;

    public MemberLoginController(@Qualifier("basicLogin") MemberLoginService memberLoginService){
        this.memberLoginService = memberLoginService;
    }

    @PostMapping("/memberLogin")
    public ResponseEntity<Map<String, Object>> memberLogin(@RequestBody MemberLoginDTO memberLoginDTO, HttpServletRequest request){
        Map<String, Object> response = new HashMap<>();

        MemberSuccessLoginDTO memberSuccessLoginDTO = memberLoginService.memberLogin(memberLoginDTO);

        HttpSession oldSession = request.getSession(false);

        if(oldSession!=null){
            oldSession.invalidate();
        }
        HttpSession newSession = request.getSession(true);
        newSession.setAttribute("loginEmail", memberSuccessLoginDTO.getEmail());
        String loginEmail = (String) newSession.getAttribute("loginEmail");
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
