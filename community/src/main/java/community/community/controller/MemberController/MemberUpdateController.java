package community.community.controller.MemberController;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.dto.MemberDTO.MemberPasswordDTO;
import community.community.entity.Member;
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

    @GetMapping("/findMember/{id}")
    public ResponseEntity<Map<String, Object>> findMember(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();

         MemberDTO memberDTO = memberUpdateInterface.findMember(id);

        response.put("message", "회원조회를 시작합니다");
        response.put("email", memberDTO.getEmail());
        response.put("password", memberDTO.getPassword());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/updateMember/{id}")
    public ResponseEntity<Map<String, Object>> updateMember(@PathVariable("id") Long id, @RequestBody MemberPasswordDTO memberPasswordDTO){
        Map<String, Object> response = new HashMap<>();

        Member member =memberUpdateInterface.updatePassword(id, memberPasswordDTO);

        response.put("message", "회원님의 비밀번호가 변경되었습니다.");
        response.put("password",member.getPassword());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteMember/{id}")
    public ResponseEntity<Map<String, Object>> deleteMember(@PathVariable("id") Long id,@RequestBody MemberPasswordDTO memberPasswordDTO){
        Map<String, Object> response = new HashMap<>();

        memberUpdateInterface.deleteMember(id, memberPasswordDTO);

        response.put("message", "회원탈퇴가 정상적으로 이루어졌습니다.");

        return ResponseEntity.ok(response);
    }

}
