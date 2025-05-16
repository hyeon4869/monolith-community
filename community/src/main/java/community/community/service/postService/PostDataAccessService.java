package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import java.util.List;

public interface PostDataAccessService {
    List<PostFindDTO> findRecentPostsByIds(List<Long> postIds);
    void addRecentPostToUser(String loginEmail, Long postId, List<Long> currentPosts);
}
