package community.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class PostFile extends BasicTimeEntity{

    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String fileName;
    private String filePath;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void setPost(Post post){
        this.post = post;
    }

}
