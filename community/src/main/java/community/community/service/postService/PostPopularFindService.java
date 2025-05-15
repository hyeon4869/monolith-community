package community.community.service.postService;


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
public class PostPopularFindService {

    private final PostRecentRepository postRecentRepository;

    private final ConcurrentHashMap<String, List<Long>> recentPost = new ConcurrentHashMap<>();

    //상세보기 페이지에 접속했을 때 실행되는 메서드

    public void addRecentPosts(HttpSession session, Long postId){
        String userEmail =(String) session.getAttribute("loginEmail");

        if(userEmail == null) {
            return;
        }
        List<Long> posts = recentPost.get(userEmail);
        if(posts == null) {
            List<UserRecentPost> userRecentPost = postRecentRepository.findByUserEmail(userEmail);

            if(userRecentPost != null && !userRecentPost.isEmpty()) {
                posts = new ArrayList<>();

                for(int i = 0; i < userRecentPost.size(); i++){
                    posts.add(userRecentPost.get(i).getPostId());
                }
            } else {
                posts = new ArrayList<>();
            }

            recentPost.put(userEmail, posts);
        }

        //중복 객체 제거
        posts.remove(postId);
        posts.add(0, postId);

        if(posts.size() > 10) {
            posts.remove(posts.size() - 1);
        }
        //없으면 db에서 데이터를 확인해보고 있으면 들고오고
        //없으면 새롭게 추가

    }

    @Transactional
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
    //사용자가 로그아웃 할 때는 hashMap에 저장된 사용자의 기록을 삭제시키고 db에 저장

}
