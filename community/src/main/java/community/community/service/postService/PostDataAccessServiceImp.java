package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import community.community.exception.customException.DBAccessException;
import community.community.repository.postRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostDataAccessServiceImp implements PostDataAccessService {

    private final PostRepository postRepository;

    @Override
    public List<PostFindDTO> findRecentPostsByIds(List<Long> postIds) {
        if (postIds == null || postIds.isEmpty()) {
            return new ArrayList<>();
        }

        try {
            List<PostFindDTO> posts = postRepository.findRecentPosts(postIds);

            // ID를 키로 하는 맵 생성
            Map<Long, PostFindDTO> postMap = new HashMap<>();
            for (PostFindDTO post : posts) {
                postMap.put(post.getId(), post);
            }

            // 원래 순서대로 결과 리스트 구성
            List<PostFindDTO> sortedPosts = new ArrayList<>(posts.size());
            for (Long id : postIds) {
                PostFindDTO post = postMap.get(id);
                if (post != null) {
                    sortedPosts.add(post);
                }
            }
            return sortedPosts;
        } catch (DataAccessException e) {
            throw new DBAccessException("데이터베이스 접근에 문제가 발생했습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 문제가 발생했습니다.", e);
        }
    }

    @Override
    public void addRecentPostToUser(String loginEmail, Long postId, List<Long> currentPosts) {
        if (loginEmail == null || postId == null) {
            return;
        }

        // 중복 객체 제거
        currentPosts.remove(postId);
        currentPosts.add(0, postId);

        // 최대 10개 유지
        if (currentPosts.size() > 10) {
            currentPosts.remove(currentPosts.size() - 1);
        }
    }
}
