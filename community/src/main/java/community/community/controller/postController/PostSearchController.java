package community.community.controller.postController;

import community.community.dto.postDTO.PostFindDTO;
import community.community.service.postService.PostSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchService postSearchService;

    //게시글 검색
    @GetMapping("/postSearch")
    public ResponseEntity<Map<String, Object>> titleSearch(@RequestParam String title,
                                                           @RequestParam(defaultValue = "50") int size,
                                                           @RequestParam(defaultValue = "0") int page){
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of(page, size);
        Page<PostFindDTO> postFindDTOS = postSearchService.postSearch(pageable, title);

        response.put("검색된 내용", postFindDTOS.getContent());
        return ResponseEntity.ok(response);
    }
}
