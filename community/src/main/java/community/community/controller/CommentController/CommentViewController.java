package community.community.controller.CommentController;

import community.community.dto.commentDTO.CommentViewDTO;
import community.community.service.commentService.CommentViewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CommentViewController {

    private CommentViewService commentViewService;

    public CommentViewController(CommentViewService commentViewService){
        this.commentViewService=commentViewService;
    }

    @GetMapping("/postComment/{id}")
    public ResponseEntity<Map<String, Object>> postView(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();
        List<CommentViewDTO> commentViewDTOS = commentViewService.commentView(id);
        response.put("comment", commentViewDTOS);

        return ResponseEntity.ok(response);
    }
}

