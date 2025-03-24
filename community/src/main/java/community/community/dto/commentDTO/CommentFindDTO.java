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
public class CommentFindDTO {

    private Long id;

    public static CommentFindDTO toDTO(Comment comment){
        return CommentFindDTO.builder()
                .id(comment.getId())
                .build();
    }
}
