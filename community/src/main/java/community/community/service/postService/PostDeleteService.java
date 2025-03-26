package community.community.service.postService;

import jakarta.servlet.http.HttpSession;

public interface PostDeleteService {

    //게시글 삭제
    void Delete(Long id, HttpSession session);
}
