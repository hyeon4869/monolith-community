package community.community.controller.commentController;

import community.community.dto.commentDTO.CommentFindDTO;
import community.community.dto.commentDTO.CursorResult;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.service.commentService.CommentFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CommentFindController {

    private final CommentFindService commentFindService;

    @GetMapping("/postComment/{id}")
    public ResponseEntity<Map<String, Object>> postView(@PathVariable("id") Long id,
                                                        @RequestParam(required = false) Long cursor,
                                                        @RequestParam(defaultValue = "10") int size){
        Map<String, Object> response = new HashMap<>();
        CursorResult<CommentFindDTO> commentFindDTOS = commentFindService.findComment(id, cursor, size);
        response.put("comment", commentFindDTOS);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/replicaComment/{commentId}")
    public ResponseEntity<Map<String, Object>> repliesView(@PathVariable("commentId") Long commentId,
                                                           @RequestParam(required = false) Long cursor,
                                                           @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> response = new HashMap<>();
        CursorResult< RepliesCommentRegisterDTO> repliesCommentRegisterDTOS = commentFindService.findRepliesComment(commentId, cursor, size);
        response.put("replies", repliesCommentRegisterDTOS);
        return ResponseEntity.ok(response);
    }


}

