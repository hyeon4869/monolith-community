package community.community.mapper;

import community.community.dto.postDTO.PostDTO;
import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostRegisterDTO;
import community.community.entity.Post;

public class PostMapper {

    public static PostDetailDTO toPostDetailDTO(Post post) {
        return PostDetailDTO.builder()
                    .id(post.getId())
                    .content(post.getContent())
                    .title(post.getTitle())
                    .createTime(post.getCreateTime())
                    .build();
    }

    public static PostRegisterDTO toPostRegisterDTO(Post post, String message) {
        return PostRegisterDTO.builder()
                    .content(post.getContent())
                    .message(message)
                    .title(post.getTitle())
                    .createTime(post.getCreateTime())
                    .memberId(post.getMember().getId())
                    .memberEmail(post.getMember().getEmail())
                    .build();
    }

    //////////////////////////////////////////////
    //엔티티로 변환하는 로직

    //postDTO
    public static Post toEntity(PostDTO postDTO) {
        return Post.builder()
                    .title(postDTO.getTitle())
                    .content(postDTO.getContent())
                    .build();
    }


}
