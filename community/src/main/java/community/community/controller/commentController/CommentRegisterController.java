package community.community.controller.commentController;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.service.commentService.CommentRegisterService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentRegisterController {

    private final CommentRegisterService commentRegisterService;

    //본댓글 작성
    @PostMapping("/write/{id}")
    public ResponseEntity<Map<String, Object>> commentRegister(@PathVariable("id") Long id, @RequestBody CommentRegisterDTO commentRegisterDTO, HttpSession session){

        Map<String, Object> response = new HashMap<>();
        String content=commentRegisterService.commentRegister(id, commentRegisterDTO,session);
        response.put("댓글", content);
        return ResponseEntity.ok(response);
    }

    //대댓글 작성
    @PostMapping("/replicaComment/{commentId}")
    public ResponseEntity<Map<String, Object>> replicaRegister(@PathVariable("commentId") Long commentId, @RequestBody RepliesCommentRegisterDTO repliesCommentRegisterDTO, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        String content=commentRegisterService.replicaCommentRegister(commentId, repliesCommentRegisterDTO,session);
        response.put("대댓글", content);

        return ResponseEntity.ok(response);
    }
}
