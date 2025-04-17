package community.community.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.cache.annotation.Cacheable;

@Entity
@Table(name = "Member")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Cacheable
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
public class Member extends BasicTimeEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    //패스워드 수정 메서드
    public void updatePassword(String password){
        validateNullOrEmpty("비밀번호", password);
        this.password=password;
    }


    //null 또는 empty 확인 메서드
    public void validateNullOrEmpty(String fieldName, String value){
        if(value==null||value.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName+"이(가) null이거나 비어있습니다.");
        }
    }

}
