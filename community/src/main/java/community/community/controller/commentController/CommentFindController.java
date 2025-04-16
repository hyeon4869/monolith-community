package community.community.controller.commentController;

import community.community.dto.commentDTO.CommentViewDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.service.commentService.CommentFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentFindController {

    private final CommentFindService commentFindService;

    @GetMapping("/postComment/{id}")
    public ResponseEntity<Map<String, Object>> postView(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        List<CommentViewDTO> commentViewDTOS = commentFindService.findComment(id);
        response.put("comment", commentViewDTOS);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/replicaComment/{commentId}")
    public ResponseEntity<Map<String, Object>> repliesView(@PathVariable("commentId") Long commentId) {
        Map<String, Object> response = new HashMap<>();
        List< RepliesCommentRegisterDTO> repliesCommentRegisterDTOS = commentFindService.findRepliesComment(commentId);
        response.put("replies", repliesCommentRegisterDTOS);
        return ResponseEntity.ok(response);
    }

}

