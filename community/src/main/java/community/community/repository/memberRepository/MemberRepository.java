package community.community.repository.memberRepository;

import community.community.entity.Member;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);


    //읽기 전용으로 아이디 조회
    @Query("SELECT m FROM Member m where m.id= :id")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Member> findByReadId(Long id);

    //읽기 전용으로 이메일 조회
    @Query("SELECT m FROM Member m where m.email= :email")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Optional<Member> findByReadEmail(String email);

    //읽기 전용으로 모든 회원 조회
    @Query("SELECT m FROM Member m")
    @QueryHints(@QueryHint(name = "org.hibernate.readOnly", value = "true"))
    List<Member> findReadMemberList();


}
