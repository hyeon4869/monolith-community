package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSearchService {

    Page<PostFindDTO> postSearch(Pageable pageable,String title);
}
