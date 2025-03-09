package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindAllDTO;
import community.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostFindService {

    Page<PostFindAllDTO> postFindAll(Pageable pageable);


    PostDetailDTO postDetail(Long id);

    Post postFindId(Long id);
}
