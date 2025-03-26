package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchService {

    //제목으로 검색
    Page<PostFindDTO> postSearch(Pageable pageable,String title);
}
