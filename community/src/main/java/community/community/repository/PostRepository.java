package community.community.repository;

import community.community.entity.Post;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    //Spring data JPA를 활용한 방법
    //N+1 문제를 해결해야함

    @Query("SELECT p, m FROM Post p JOIN FETCH p.member m")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value="true"))
    List<Post> findAllRead();


    //읽기 전용 쿼리 힌트 사용
    @Query("SELECT p FROM Post p WHERE p.id=:id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value ="true"))
    Optional<Post> findByReadId(Long id);

}
