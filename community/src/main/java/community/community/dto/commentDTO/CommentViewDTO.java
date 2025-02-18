package community.community.dto.commentDTO;

import community.community.entity.Comment;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentViewDTO {
    private Long id;
    private String content;
    private String writer;
    private Date createTime;
    //static 사용으로 객체 생성없이 사용
    //builder를 통해 내부적으로 객체 생성하여 불변성 유지
    public static CommentViewDTO toFromEntity(Comment comment){
        CommentViewDTO commentViewDTO = CommentViewDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .writer(comment.getWriter())
                .createTime(comment.getCreateTime())
                .build();
            return commentViewDTO;

            //git check

=======
    }
}
