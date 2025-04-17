package community.community.repository.commentRepository;

import community.community.dto.commentDTO.CommentOfMemberDTO;
import community.community.entity.Comment;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    @Query("SELECT c FROM Comment c JOIN FETCH c.post p where p.id=:id AND c.parentComment IS NULL ORDER BY c.createTime")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Comment> findReadAll(@Param("id") Long id);

    //모든 대댓글을 조회
    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentId ORDER BY c.createTime ")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Comment> findRepliesReadAll(@Param("parentId") Long parentId);

    //좋아요 카운트 1 증가 쿼리
    @Modifying
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount+1 where c.id=:id ")
    void increaseLikeCount(Long id);

    //좋아요 카운트 1 증가 쿼리
    @Modifying
    @Query("UPDATE Comment c SET c.likeCount = c.likeCount-1 where c.id=:id ")
    void decreaseLikeCount(Long id);

    @Query("SELECT new community.community.dto.commentDTO.CommentOfMemberDTO(c.id, c.content, p.title)"+
    "FROM Comment c JOIN c.post p Where c.member.id = :id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Page<CommentOfMemberDTO> findAllCommentByMemberId(Pageable pageable,@Param("id") Long id);
}
