package community.community.repository.postRepository;

import community.community.entity.PostFile;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {

    @Query("SELECT pf FROM PostFile pf WHERE pf.post.id = :id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    PostFile findReadPostId(Long id);
}
