package community.community.controller.PostController;

import community.community.dto.postDTO.PostFindDTO;
import community.community.service.postService.PostSearchService;
import org.springframework.beans.factory.annotation.Qualifier;
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
public class PostSearchController {

    private final PostSearchService postSearchService;

    public PostSearchController(@Qualifier("BASIC SEARCH") PostSearchService postSearchService){
        this. postSearchService=postSearchService;
    }

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
