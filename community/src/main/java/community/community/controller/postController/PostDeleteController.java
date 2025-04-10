package community.community.controller.postController;

import community.community.service.postService.PostDeleteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostDeleteController {

    private final PostDeleteService postDeleteService;

    //게시글 삭제
    @PatchMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable("id") Long id, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        postDeleteService.Delete(id, session);
        response.put("messsage", "게시물이 삭제되었습니다.");
        return ResponseEntity.ok(response);
    }
}
