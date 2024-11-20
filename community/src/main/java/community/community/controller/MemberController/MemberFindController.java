package community.community.controller.MemberController;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.MemberDTO.MemberMyPageDTO;
import community.community.service.memberService.MemberFindService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class MemberFindController {

    private final MemberFindService memberFindService;

    public MemberFindController(@Qualifier("defaultFind") MemberFindService memberFindService){
        this.memberFindService=memberFindService;
    }

    @GetMapping("/findMember/{id}")
    public ResponseEntity<Map<String, Object>> findMember(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();

        MemberIdAndEmailDTO memberIdAndEmailDTO = memberFindService.findMember(id);

        response.put("message", "회원조회 완료");
        response.put("email", memberIdAndEmailDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myPage/{id}")
    public ResponseEntity<Map<String, Object>> findMemberAndPost(@PathVariable("id") Long id){
        Map<String,Object> response = new HashMap<>();

        MemberMyPageDTO memberMyPageDTO=memberFindService.findMemberAndPost(id);
        response.put("myPage", memberMyPageDTO);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/findMember")
    public ResponseEntity<Map<String, Object>> findMemberList(){
        Map<String, Object> response = new HashMap<>();
        List<MemberIdAndEmailDTO> memberIdAndEmailDTOList = memberFindService.findMemberList();
        response.put("member", memberIdAndEmailDTOList);

        return ResponseEntity.ok(response);
    }
}
