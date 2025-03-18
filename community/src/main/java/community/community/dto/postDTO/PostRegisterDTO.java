package community.community.dto.postDTO;

import community.community.entity.Post;
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

    public static PostRegisterDTO toDTO(Post post, String message){
        return PostRegisterDTO.builder()
                .content(post.getContent())
                .message(message)
                .title(post.getTitle())
                .createTime(post.getCreateTime())
                .memberId(post.getMember().getId())
                .memberEmail(post.getMember().getEmail())
                .build();
    }
}
