package community.community.dto.postDTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDetailDTO {

    private Long id;
    private String title;
    private String content;
    private Date createTime;
}
