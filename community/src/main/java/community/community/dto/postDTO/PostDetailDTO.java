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


    public static PostDetailDTO toFromEntity(Post post){
        PostDetailDTO postDetailDTO = PostDetailDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .createTime(post.getCreateTime())
                .build();
        return postDetailDTO;
    }

//    //정적 메소드로 수정하기
//    PostDetailDTO postDetailDTO = new PostDetailDTO();
//        postDetailDTO.setCreateTime(post.getCreateTime());
//        postDetailDTO.setId(post.getId());
//        postDetailDTO.setTitle(post.getTitle());
//        postDetailDTO.setContent(post.getContent());
//        return postDetailDTO;

}
