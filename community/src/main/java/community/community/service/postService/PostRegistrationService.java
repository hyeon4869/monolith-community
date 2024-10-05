package community.community.service.postService;

import community.community.dto.postDTO.PostDTO;
import community.community.entity.Post;
import jakarta.servlet.http.HttpSession;

public interface PostRegistrationService {

    //게시글 등록 메소드
    Post postRegistration(PostDTO postDTO, HttpSession session);
}
