package community.community.dto.postDTO;

import community.community.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOfMemberDTO {

    private Long id;
    private String title;
    private int LikeCount;

    public static PostOfMemberDTO toDTO (Post post){
        return PostOfMemberDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .LikeCount(post.getLikeCount())
                .build();
    }
}
