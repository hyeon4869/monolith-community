package community.community.dto.postDTO;

import community.community.entity.Member;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class PostDTO {

    private Long id;
    private String title;
    private String content;
    private Member member;
    private MultipartFile file;
}
