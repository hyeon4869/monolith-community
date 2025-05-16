package community.community.controller.myPageController;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.commentDTO.CommentOfMemberDTO;
import community.community.dto.postDTO.PostOfMemberDTO;
import community.community.service.myPageService.MyPageServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    private final MyPageServiceImp myPageService;

    @GetMapping("/myPageInfoMember/{id}")
    public ResponseEntity<Map<String, Object>> myPagebyMember(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        MemberIdAndEmailDTO memberIdAndEmailDTO = myPageService.findMember(id);
        response.put("member", memberIdAndEmailDTO);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myPageInfoPost/{id}")
    public ResponseEntity<Map<String, Object>> myPageByPost(@PathVariable("id") Long id,
                                                              @RequestParam(defaultValue = "10") int size,
                                                              @RequestParam(defaultValue = "0") int page) {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of(page, size);
        Page<PostOfMemberDTO> postOfMemberDTOS = myPageService.findPostByMemberId(pageable, id);

        response.put("postInfo",postOfMemberDTOS);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/myPageInfoComment/{id}")
    public ResponseEntity<Map<String, Object>> myPageByComment(@PathVariable("id") Long id,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "0" ) int page){

        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, size);

        Page<CommentOfMemberDTO> commentOfMemberDTO = myPageService.findCommentByMemberId(pageable, id);

        response.put("commentInfo", commentOfMemberDTO);
        return ResponseEntity.ok(response);
    }

}
