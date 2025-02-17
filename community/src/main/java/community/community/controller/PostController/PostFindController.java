package community.community.controller.PostController;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.service.postService.PostFindService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PostFindController {

    private final PostFindService postFindService;

    public PostFindController(PostFindService postFindService){
        this.postFindService = postFindService;
    }

    @GetMapping("/postFindAll")
    public ResponseEntity<Map<String, Object>> postFindAll(
            @RequestParam(defaultValue = "0") int page, // 기본 페이지 번호: 0
            @RequestParam(defaultValue = "50") int size // 기본 페이지 크기: 10
    ) {
        Map<String, Object> response = new HashMap<>();

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // Service 계층에서 페이징 처리된 결과 호출
        Page<PostFindDTO> postPage = postFindService.postFindAll(pageable);

        // 응답 데이터 구성
        response.put("message", "게시글 조회에 성공하였습니다.");
        response.put("게시물", postPage.getContent()); // 현재 페이지의 게시물 리스트
        response.put("현재페이지", postPage.getNumber()); // 현재 페이지 번호
        response.put("전체페이지수", postPage.getTotalPages()); // 전체 페이지 수
        response.put("전체게시물수", postPage.getTotalElements()); // 전체 게시물 수
        response.put("페이지크기", postPage.getSize()); // 한 페이지당 게시물 수

        return ResponseEntity.ok(response);
    }

    @GetMapping("/postDetail/{id}")
    public ResponseEntity<Map<String, Object>> postDetail(@PathVariable("id") Long id){
        Map<String, Object> response = new HashMap<>();

        PostDetailDTO postDetailDTO = postFindService.postDetail(id);
        response.put("message", "게시물 내용을 불러옵니다.");
        response.put("title", postDetailDTO.getTitle());
        response.put("createTime", postDetailDTO.getCreateTime());
        response.put("content", postDetailDTO.getContent());

        return ResponseEntity.ok(response);
    }
}
