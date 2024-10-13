package community.community.dto.commentDTO;

import community.community.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRegisterDTO {

    private Long id;

    private String Content;

    private String writer;

    private Post postId;
}
