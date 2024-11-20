package community.community.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Member")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Member extends BasicTimeEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();


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
