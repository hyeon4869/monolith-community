package community.community.entity;

import community.community.dto.MemberDTO.MemberDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Member")
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "member")
    private List<Post> postList = new ArrayList<>();


    //member객체로 변환 로직
    public void convertToMember(MemberDTO memberDTO){
        this.id = memberDTO.getId();
        this.email=memberDTO.getEmail();
        this.password=memberDTO.getPassword();
    }

    public void setPassword(String password){
        this.password=password;
    }
}
