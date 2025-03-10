package community.community.dto.commentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepliesCommentRegisterDTO {

    private Long id;

    private Long parentCommentId;

    private String content;

}
