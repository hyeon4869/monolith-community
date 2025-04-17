package community.community.dto.commentDTO;

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
}
