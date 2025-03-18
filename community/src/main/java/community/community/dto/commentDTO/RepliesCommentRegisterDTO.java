package community.community.dto.commentDTO;

import community.community.entity.Comment;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RepliesCommentRegisterDTO {

    private Long id;

    private Long parentCommentId;

    private String content;

    private Date createTime;

    public static RepliesCommentRegisterDTO toDTO(Comment comment){
        RepliesCommentRegisterDTO repliesCommentRegisterDTO = RepliesCommentRegisterDTO.builder()
                .content(comment.getContent())
                .createTime(comment.getCreateTime())
                .build();
        return repliesCommentRegisterDTO;
    }

}
