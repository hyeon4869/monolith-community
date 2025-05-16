package community.community.repository.postRepository;

import community.community.dto.postDTO.PostFindAllDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.dto.postDTO.PostOfMemberDTO;
import community.community.entity.Post;
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
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //소프트삭제 제외하고 조회
    @Query("SELECT new community.community.dto.postDTO.PostFindAllDTO(p.id, p.title, m.email, p.likeCount) " +
            "FROM Post p JOIN p.member m WHERE p.isDeleted=false ORDER BY p.createTime DESC")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value ="true"))
    Page<PostFindAllDTO> findAllPostWithEmailWithLike(Pageable pageable);


    //단일 회원 게시물 전체 조회
    @Query("SELECT new community.community.dto.postDTO.PostOfMemberDTO(p.id, p.title, p.likeCount) "+
    "FROM Post p JOIN p.member m WHERE p.isDeleted=false AND p.member.id = :id ORDER BY p.createTime DESC")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Page<PostOfMemberDTO> findAllPostByMemberId(Pageable pageable, @Param("id") Long id);

    //읽기 전용 쿼리 힌트 사용
    @Query("SELECT DISTINCT p FROM Post p WHERE p.id=:id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value ="true"))
    Optional<Post> findByReadId(Long id);

    //키워드로 조회
    //대소문자 구분없이 조회함
    //ex) a 로 입력해도 A 게시물 조회 가능
    @Query("SELECT new community.community.dto.postDTO.PostFindDTO(p.id, p.title, m.email)" +
            "FROM Post p JOIN p.member m WHERE p.isDeleted=false AND LOWER(p.title) LIKE LOWER(CONCAT('%',:title,'%')) ORDER BY p.createTime DESC")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value="true"))
    Page<PostFindDTO> findSearchTitle(Pageable pageable, String title);


    //최근 조회한 게시물
    @Query("SELECT new community.community.dto.postDTO.PostFindDTO(p.id, p.title, m.email)" +
    "FROM Post p JOIN p.member m WHERE p.isDeleted=false AND p.id IN :postId")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value="true"))
    List<PostFindDTO> findRecentPosts(@Param("postId") List<Long> postId);

    //좋아요 카운트 1감소 update 쿼리
    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount - 1 WHERE p.id = :id")
    void decreaseLikeCount(Long id);

    //좋아요 카운트 1증가 update 쿼리
    @Modifying
    @Query("UPDATE Post p SET p.likeCount = p.likeCount + 1 WHERE p.id = :id")
    void increaseLikeCount(Long id);

}