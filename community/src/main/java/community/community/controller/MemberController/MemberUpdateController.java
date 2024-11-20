package community.community.controller.MemberController;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.MemberDTO.MemberPasswordDTO;
import community.community.service.memberService.MemberUpdateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MemberUpdateController {

    private final MemberUpdateService memberUpdateService;

    public MemberUpdateController(@Qualifier("defaultUpdate") MemberUpdateService memberUpdateService){
        this.memberUpdateService = memberUpdateService;
    }

    @PutMapping("/updateMember/{id}")
    public ResponseEntity<Map<String, Object>> updateMember(@PathVariable("id") Long id, @RequestBody MemberPasswordDTO memberPasswordDTO){
        Map<String, Object> response = new HashMap<>();

        MemberIdAndEmailDTO memberIdAndEmailDTO = memberUpdateService.updatePassword(id, memberPasswordDTO);

        response.put("message", "회원님의 비밀번호가 변경되었습니다.");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteMember/{id}")
    public ResponseEntity<Map<String, Object>> deleteMember(@PathVariable("id") Long id,@RequestBody MemberPasswordDTO memberPasswordDTO){
        Map<String, Object> response = new HashMap<>();

        memberUpdateService.deleteMember(id, memberPasswordDTO);

        response.put("message", "회원탈퇴가 정상적으로 이루어졌습니다.");

        return ResponseEntity.ok(response);
    }

}
