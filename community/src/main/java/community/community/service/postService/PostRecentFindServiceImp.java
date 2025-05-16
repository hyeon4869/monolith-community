package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.UserRecentPost;
import community.community.repository.postRepository.PostRecentRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostRecentFindServiceImp implements PostRecentFindService {

    private final PostRecentRepository postRecentRepository;
    private final PostDataAccessService postDataAccessService; // PostFindService 대신 이것을 사용

    private final ConcurrentHashMap<String, List<Long>> recentPost = new ConcurrentHashMap<>();

    //상세보기 페이지에 접속했을 때 실행되는 메서드
    @Override
    public void addRecentPosts(HttpSession session, Long postId){
        String loginEmail = (String) session.getAttribute("loginEmail");

        if(loginEmail == null) {
            return;
        }

        List<Long> posts = recentPost.get(loginEmail);
        if(posts == null) {
            List<UserRecentPost> userRecentPost = postRecentRepository.findByUserEmail(loginEmail);

            if(userRecentPost != null && !userRecentPost.isEmpty()) {
                posts = new ArrayList<>();

                for(int i = 0; i < userRecentPost.size(); i++){
                    posts.add(userRecentPost.get(i).getPostId());
                }
            } else {
                posts = new ArrayList<>();
            }

            recentPost.put(loginEmail, posts);
        }

        // PostDataAccessService로 로직 위임
        postDataAccessService.addRecentPostToUser(loginEmail, postId, posts);
    }

    @Transactional
    @Override
    public void saveRecentPost(String loginEmail) {
        List<Long> posts = recentPost.computeIfAbsent(loginEmail, k -> new ArrayList<>());

        if (posts != null && !posts.isEmpty()) {
            //메모리의 데이터 정리
            recentPost.remove(loginEmail);

            // 기존 데이터 삭제
            postRecentRepository.deleteByUserEmail(loginEmail);

            // 새 데이터 일괄 준비
            List<UserRecentPost> userPosts = new ArrayList<>();
            LocalDateTime now = LocalDateTime.now();
            for (int i = 0; i < posts.size(); i++) {
                UserRecentPost post = UserRecentPost.builder()
                        .userEmail(loginEmail)
                        .postId(posts.get(i))
                        .viewTimeAt(now.minusSeconds(i))
                        .build();
                userPosts.add(post);
            }

            postRecentRepository.saveAll(userPosts);
        }
    }

    @Override
    public List<PostFindDTO> findRecentPost(String loginEmail) {
        if(recentPost.containsKey(loginEmail)){
            List<Long> postId = recentPost.get(loginEmail);
            return postDataAccessService.findRecentPostsByIds(postId);
        } else {
            return new ArrayList<>();
        }
    }
}
