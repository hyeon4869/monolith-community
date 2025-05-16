package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface PostRecentFindService {

    //map에 최근 글 추가
    void addRecentPosts(HttpSession session, Long postId);

    //db에 저장 후 map의 특정 데이터를 삭제
    void saveRecentPost(String loginEmail);

    //최근 게시물 반환 기능
    List<PostFindDTO> findRecentPost(String loginEmail);
}
