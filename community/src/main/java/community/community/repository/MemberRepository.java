package community.community.repository;

import community.community.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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

}
