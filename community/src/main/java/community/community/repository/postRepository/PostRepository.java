package community.community.repository.postRepository;

import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.Post;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //소프트 삭제로 처리
    @Query("SELECT new community.community.dto.postDTO.PostFindDTO(p.id, p.title, m.email) " +
            "FROM Post p JOIN p.member m WHERE isDeleted=false")
    Page<PostFindDTO> findAllPostWithEmail(Pageable pageable);


    //읽기 전용 쿼리 힌트 사용
    @Query("SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.commentList c WHERE p.id=:id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value ="true"))
    Optional<Post> findByReadId(Long id);


}
