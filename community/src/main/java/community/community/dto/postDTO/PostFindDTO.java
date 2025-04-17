package community.community.dto.postDTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostFindDTO {

    private Long id;
    private String title;
    private String memberEmail;

}
