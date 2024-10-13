package community.community.controller.CommentController;

import community.community.dto.commentDTO.CommentUpdateDTO;
import community.community.service.commentService.CommentUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentUpdateController {

    private final CommentUpdateService commentUpdateService;

    public CommentUpdateController(CommentUpdateService commentUpdateService){
        this.commentUpdateService=commentUpdateService;

    }

    @PutMapping("/commentUpdate/{id}")
    public ResponseEntity<Map<String, Object>> updateComment(@PathVariable Long id, @RequestBody CommentUpdateDTO commentUpdateDTO){
        Map<String, Object> response = new HashMap<>();
        CommentUpdateDTO content=commentUpdateService.findCommentId(id, commentUpdateDTO);
        response.put("comment", commentUpdateDTO);
        return ResponseEntity.ok(response);
    }


}
