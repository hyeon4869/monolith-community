package community.community.dto.postDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostFindDTO {

    private Long id;

    private String title;

    private String memberEmail;

}
