package community.community.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BasicTimeEntity{

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    //양방향 연관관계 메서드
    public void setPost(Post post){
        if(this.post!=null){
            this.post.getCommentList().remove(this);
        }
        this.post=post;
        post.getCommentList().add(this);
    }

    public void setContent(String content){
        this.content=content;
    }

}
