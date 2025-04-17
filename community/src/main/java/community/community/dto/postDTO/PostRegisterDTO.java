package community.community.dto.postDTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRegisterDTO {

    public Long id;
    public String message;
    public String title;
    public Date createTime;
    public String content;
    public Long memberId;
    public String memberEmail;

}
