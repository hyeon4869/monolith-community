package community.community.controller.CommentController;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.entity.Comment;
import community.community.service.commentService.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentRegisterController {

    private final CommentService commentService;

    public CommentRegisterController(CommentService commentService){
        this. commentService=commentService;
    }

    @PostMapping("/postDetail/{id}")
    public ResponseEntity<Map<String, Object>> register(@RequestBody CommentRegisterDTO commentRegisterDTO, HttpSession session, @PathVariable Long id){
        Map<String, Object> response = new HashMap<>();
        String writer = (String) session.getAttribute("loginEmail");

        Comment comment=commentService.register(commentRegisterDTO, writer, id);

        response.put("Comment", comment);
        return ResponseEntity.ok(response);
    }

}
