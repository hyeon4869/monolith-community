package community.community.dto.commentDTO;

import community.community.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentOfMemberDTO {

    private Long id;
    private String content;
    private String postTitle;

    public static CommentOfMemberDTO toDTO(Comment comment){
        return CommentOfMemberDTO.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .postTitle(comment.getPost().getTitle())
                .build();
    }
}
