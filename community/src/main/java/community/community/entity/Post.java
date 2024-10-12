package community.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "Post")
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    private Member member;

    //엔티티 변환 로직


    //양방향 연관관계 편의 메서드
    public void setMember(Member member){
        if(this.member!=null){
            this.member.getPostList().remove(this);
        }
        this.member=member;
        member.getPostList().add(this);
    }


}
