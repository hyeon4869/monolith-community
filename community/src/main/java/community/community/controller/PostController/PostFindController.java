package community.community.controller.PostController;

import community.community.dto.postDTO.PostFindDTO;
import community.community.interfaceService.postInterface.PostFindInterface;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PostFindController {

    private final PostFindInterface postFindInterface;

    public PostFindController(PostFindInterface postFindInterface){
        this.postFindInterface=postFindInterface;
    }

    @GetMapping("/postFindAll")
    public ResponseEntity<Map<String, Object>> postFindAll(){
        Map<String, Object> response = new HashMap<>();

        List<PostFindDTO> postFindDTOList=postFindInterface.postFindAll();
        response.put("message", "게시글 조회에 성공하였습니다.");
        response.put("게시물",postFindDTOList);
        return ResponseEntity.ok(response);
    }
}