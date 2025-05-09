package community.community.mapper;

import community.community.dto.postDTO.PostDTO;
import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFileDTO;
import community.community.dto.postDTO.PostRegisterDTO;
import community.community.entity.Post;
import community.community.entity.PostFile;

public class PostMapper {

    public static PostDetailDTO toPostDetailDTO(Post post, PostFile postFile) {
        return PostDetailDTO.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .createTime(post.getCreateTime())
                .postFileDTO(postFile != null ?
                        PostFileDTO.builder()
                        .fileName(postFile.getFileName())
                        .filePath(postFile.getFilePath())
                        .build() : null)
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
        return community.community.entity.Post.builder()
                    .title(postDTO.getTitle())
                    .content(postDTO.getContent())
                    .build();
    }

    //파일 저장하는 메소드
    public static PostFile toEntity(String fileName, String filePath, Post post){
        return community.community.entity.PostFile.builder()
                .fileName(fileName)
                .filePath(filePath)
                .post(post)
                .build();
    }

}
