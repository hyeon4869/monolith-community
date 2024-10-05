package community.community.controller.PostController;

import community.community.dto.postDTO.PostDTO;
import community.community.entity.Post;
import community.community.service.postService.PostRegistrationService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PostRegistrationController {

    private final PostRegistrationService postRegistrationService;

    public PostRegistrationController(PostRegistrationService postRegistrationService){
        this.postRegistrationService = postRegistrationService;
    }

    @PostMapping("/postRegistration")
    public ResponseEntity<Map<String, Object>> postRegistration(@RequestBody PostDTO postDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        Post post = postRegistrationService.postRegistration(postDTO, session);
        response.put("message", "게시물을 등록했습니다.");
        response.put("title", post.getTitle());
        response.put("content",post.getContent());
        response.put("memberId",post.getMember().getId());
        response.put("memberEmail",post.getMember().getEmail());
        return ResponseEntity.ok(response);
    }
}
