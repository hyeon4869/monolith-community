package community.community.dto.commentDTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentFindDTO {

    private Long id;
    private String content;
    private String writer;
    private Date createTime;

}
