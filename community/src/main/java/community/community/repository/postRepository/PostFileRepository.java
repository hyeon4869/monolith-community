package community.community.repository.postRepository;

import community.community.entity.PostFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostFileRepository extends JpaRepository<PostFile, Long> {
}
