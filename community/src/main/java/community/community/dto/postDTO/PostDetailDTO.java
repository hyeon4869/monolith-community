package community.community.dto.postDTO;

import community.community.entity.Comment;
import community.community.entity.Post;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDetailDTO {

    private Long id;

    private String title;

    private String content;

    private List<Comment> commentList=new ArrayList<>();

    private Date createTime;

    public static PostDetailDTO toFromEntity(Post post){
       PostDetailDTO postDetailDTO = PostDetailDTO.builder()
               .createTime(post.getCreateTime())
               .content(post.getContent())
               .title(post.getTitle())
               .id(post.getId())
               .commentList(post.getCommentList())
               .build();
       return postDetailDTO;
    }


}
