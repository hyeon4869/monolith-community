package community.community.controller.CommentController;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.service.commentService.CommentRegisterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentRegisterController {

    private final CommentRegisterService commentRegisterService;

    public CommentRegisterController(CommentRegisterService commentRegisterService){
        this.commentRegisterService=commentRegisterService;
    }

    @PostMapping("/write/{id}")
    public ResponseEntity<Map<String, Object>> commentRegister(CommentRegisterDTO commentRegisterDTO){

        Map<String, Object> response = new HashMap<>();
        commentRegisterService.commentRegister(commentRegisterDTO);
        return ResponseEntity.ok(response);
    }
}
