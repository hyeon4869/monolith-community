package community.community.interfaceService.postInterface;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;

import java.util.List;

public interface PostFindInterface {
    List<PostFindDTO> postFindAll();

    PostDetailDTO postDetail(Long id);
}
