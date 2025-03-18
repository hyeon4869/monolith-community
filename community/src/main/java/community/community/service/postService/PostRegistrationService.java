package community.community.service.postService;

import community.community.dto.postDTO.PostDTO;
import community.community.dto.postDTO.PostRegisterDTO;
import jakarta.servlet.http.HttpSession;

public interface PostRegistrationService {

    //게시글 등록 메소드
    PostRegisterDTO postRegistration(PostDTO postDTO, HttpSession session);
}
