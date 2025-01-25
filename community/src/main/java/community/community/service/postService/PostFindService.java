package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostFindService {
    Page<PostFindDTO> postFindAll(Pageable pageable);

    PostDetailDTO postDetail(Long id);
}
