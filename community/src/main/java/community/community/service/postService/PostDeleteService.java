package community.community.service.postService;

import jakarta.servlet.http.HttpSession;

public interface PostDeleteService {

    void Delete(Long id, HttpSession session);
}
