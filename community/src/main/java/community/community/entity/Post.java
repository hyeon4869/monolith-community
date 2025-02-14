package community.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Post extends BasicTimeEntity{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;

    @Column(nullable = false)
    private boolean isDeleted;

    //양방향 연관관계 편의 메서드
    public void setMember(Member member){
        if(this.member!=null){
            this.member.getPostList().remove(this);
        }
        this.member=member;
    }

    //소프트 삭제 메서드
    public void setIsDeleted(){
        this.isDeleted=true;
    }


}
