package community.community.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostFindAllDTO {

    private Long id;
    private String title;
    private String memberEmail;
    private int likeCount;
}
