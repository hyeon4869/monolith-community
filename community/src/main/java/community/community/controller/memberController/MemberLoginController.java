package community.community.controller.memberController;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.dto.MemberDTO.MemberSuccessLoginDTO;
import community.community.exception.customException.NotFoundMemberException;
import community.community.service.memberService.MemberLoginService;
import community.community.service.postService.PostRecentFindService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MemberLoginController {

    private final MemberLoginService memberLoginService;
    private final PostRecentFindService postRecentFindService;

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

        postRecentFindService.saveRecentPost(loginEmail);
        session.invalidate();

        response.put("message","로그아웃하셨습니다.");
        return ResponseEntity.ok(response);
    }

}
