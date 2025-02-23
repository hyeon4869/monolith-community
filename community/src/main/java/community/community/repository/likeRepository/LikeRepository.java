package community.community.repository.likeRepository;

import community.community.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<UserLike, Long> {

    Optional<UserLike> findByEntityIdAndMemberId(Long memberId,Long entityId);
}
