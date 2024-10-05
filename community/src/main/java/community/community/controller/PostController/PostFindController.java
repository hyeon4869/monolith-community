package community.community.controller.PostController;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.service.postService.PostFindService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostFindController {

    private final PostFindService postFindService;

    public PostFindController(PostFindService postFindService){
        this.postFindService = postFindService;
    }

    @GetMapping("/postFindAll")
    public ResponseEntity<Map<String, Object>> postFindAll(){
        Map<String, Object> response = new HashMap<>();

        List<PostFindDTO> postFindDTOList= postFindService.postFindAll();
        response.put("message", "게시글 조회에 성공하였습니다.");
        response.put("게시물",postFindDTOList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/postDetail/{id}")
    public ResponseEntity<Map<String, Object>> postDetail(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();

        PostDetailDTO postDetailDTO = postFindService.postDetail(id);
        response.put("message", "게시물 내용을 불러옵니다.");
        response.put("title", postDetailDTO.getTitle());
        response.put("content", postDetailDTO.getContent());

        return ResponseEntity.ok(response);
    }
}
