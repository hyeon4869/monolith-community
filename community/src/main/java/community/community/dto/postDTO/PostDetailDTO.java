package community.community.dto.postDTO;

import community.community.entity.Post;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailDTO {

    private Long id;

    private String title;

    private String content;

    private Date createTime;


    public static PostDetailDTO toDTO(Post post){
        PostDetailDTO postDetailDTO = PostDetailDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .createTime(post.getCreateTime())
                .build();
        return postDetailDTO;
    }


}
