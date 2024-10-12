package community.community.repository;

import community.community.entity.Member;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);


    @Query("SELECT m FROM Member m where m.id= :id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Member> findByReadId(Long id);

    @Query("SELECT m FROM Member m where email= :email")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Member> findByReadEmail(String email);

    @Query("SELECT m FROM Member m")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Member> findReadMemberList();

    @Query("SELECT DISTINCT m FROM Member m LEFT JOIN FETCH m.postList p WHERE m.id= :id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findByMemberAndPost(Long id);

    @Query("SELECT m FROM Member m")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Page<Member> findReadPagingFindList(Pageable pageable);
}
