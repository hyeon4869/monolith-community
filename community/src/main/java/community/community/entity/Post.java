package community.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ToString
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
    private Member member;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(nullable = false)
    private boolean isDeleted;

    //양방향 연관관계 편의 메서드
    public void setMember(Member member){
        this.member=member;
    }

    //소프트 삭제 메서드
    public void setIsDeleted(){
        this.isDeleted=true;
    }

    //좋아요 증가 메서드
    public void increaseLikeCount(){
        this.likeCount++;
    }

    //좋아요 감소 메서드
    public void decreaseLikeCount() {
        this.likeCount--;
    }
}
