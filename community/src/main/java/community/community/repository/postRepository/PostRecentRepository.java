package community.community.repository.postRepository;

import community.community.entity.UserRecentPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRecentRepository extends JpaRepository<UserRecentPost, Long> {
    List<UserRecentPost> findByUserEmail(String userEmail);

    void deleteByUserEmail(String loginEmail);
}
