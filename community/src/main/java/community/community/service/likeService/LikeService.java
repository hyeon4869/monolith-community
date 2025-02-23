package community.community.service.likeService;

import community.community.dto.likeDTO.LikePostDTO;
import community.community.entity.EntityName;
import jakarta.servlet.http.HttpSession;

public interface LikeService {

    void likePostPush(LikePostDTO likePostDTO, HttpSession session, EntityName entityName);

    void likePush(LikePostDTO likePostDTO, HttpSession session,EntityName entityName);

    void likeCommentPush(LikePostDTO likePostDTO, HttpSession session, EntityName entityName);
}
