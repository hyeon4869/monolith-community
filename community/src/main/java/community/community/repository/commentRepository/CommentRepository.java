package community.community.repository.commentRepository;

import community.community.entity.Comment;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    //본댓글만 조회
    @Query("SELECT c FROM Comment c JOIN FETCH  c.post p where p.id=:id AND c.parentComment IS NULL ORDER BY createTime")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Comment> findReadAll(@Param("id") Long id);

    //모든 대댓글을 조회
    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentId ORDER BY createTime ")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Comment> findRepliesReadAll(@Param("parentId") Long parentId);

    //좋아요 카운트 1 증가 쿼리
    @Modifying
    @Query("UPDATE Comment c SET c.like_count = c.like_count+1 where c.id=:id ")
    void increaseLikeCount(Long id);

    //좋아요 카운트 1 증가 쿼리
    @Modifying
    @Query("UPDATE Comment c SET c.like_count = c.like_count-1 where c.id=:id ")
    void decreaseLikeCount(Long id);
}
