package community.community.controller.likeController;

import community.community.dto.likeDTO.LikePostDTO;
import community.community.entity.EntityName;
import community.community.service.likeService.LikeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService){
        this.likeService=likeService;
    }

    @PostMapping("/like/post")
    public ResponseEntity<Map<String, Object>> likePost(@RequestBody LikePostDTO likePostDTO, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        likeService.likePostPush(likePostDTO,session, EntityName.POST);
        response.put("message","게시물 좋아요 완료");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/like/comment")
    public ResponseEntity<Map<String, Object>> likeComment(@RequestBody LikePostDTO likePostDTO, HttpSession session){
        Map<String, Object> response = new HashMap<>();
        likeService.likeCommentPush(likePostDTO,session, EntityName.COMMENT);
        response.put("message","댓글 좋아요 완료");
        return ResponseEntity.ok(response);
    }
}
