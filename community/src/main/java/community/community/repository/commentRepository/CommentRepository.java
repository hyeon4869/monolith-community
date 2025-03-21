package community.community.repository.commentRepository;

import community.community.entity.Comment;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH  c.post p where p.id=:id AND c.parentComment IS NULL")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Comment> findReadAll(@Param("id") Long id);

    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :parentId ORDER BY createTime ")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Comment> findRepliesReadAll(@Param("parentId") Long parentId);
}
