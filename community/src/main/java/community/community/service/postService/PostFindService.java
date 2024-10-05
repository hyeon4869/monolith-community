package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;

import java.util.List;

public interface PostFindService {
    List<PostFindDTO> postFindAll();

    PostDetailDTO postDetail(Long id);
}
