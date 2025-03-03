package community.community.dto.likeDTO;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LikePostDTO {

    private Long id;
    private String entityName;
    private Long entityId;
    private String writer;
}
