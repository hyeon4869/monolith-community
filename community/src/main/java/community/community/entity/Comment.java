package community.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "comment"
        ,indexes = @Index(name = "idx_parent_id_createTime", columnList = "parent_comment_id, create_time"))

public class Comment extends BasicTimeEntity{

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;

    private String writer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "post_id")
    private Post post;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment;

    public void setContent(String content){
        this.content=content;
    }

    public void setPost(Post post){
        this.post=post;
    }

}
