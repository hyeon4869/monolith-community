package community.community.entity;

import community.community.dto.postDTO.PostDTO;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
@Table(name = "POST")
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "postList")
//    private Member member;

    //엔티티로 변환 로직
    public void convertToPost(PostDTO postDTO){
        this.title= postDTO.getTitle();
        this.content= postDTO.getContent();
    }
}
