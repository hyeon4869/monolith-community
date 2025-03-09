package community.community.repository.likeRepository;

import community.community.entity.UserLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<UserLike, Long> {

    @Query("SELECT ul FROM UserLike ul JOIN FETCH ul.member m WHERE m.id = :memberId AND ul.entityId = :entityId")
    Optional<UserLike> findByMemberIdAndEntityId(Long memberId,Long entityId);
}
