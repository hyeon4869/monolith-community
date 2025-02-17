package community.community.controller.CommentController;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.service.commentService.CommentRegisterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class CommentRegisterController {

    private final CommentRegisterService commentRegisterService;

    public CommentRegisterController(CommentRegisterService commentRegisterService){
        this.commentRegisterService=commentRegisterService;
    }

    @PostMapping("/write/{id}")
    public ResponseEntity<Map<String, Object>> commentRegister(@PathVariable("id") Long id, @RequestBody CommentRegisterDTO commentRegisterDTO, HttpSession session){

        Map<String, Object> response = new HashMap<>();
        String content=commentRegisterService.commentRegister(id, commentRegisterDTO,session);
        response.put("댓글", content);
        return ResponseEntity.ok(response);
    }
}
