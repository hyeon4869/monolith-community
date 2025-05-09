package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindAllDTO;
import community.community.dto.postDTO.PostOfMemberDTO;
import community.community.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostFindService {

    Page<PostFindAllDTO> postFindAll(Pageable pageable);

    //게시글 상세 조회
    PostDetailDTO postDetail(Long id);

    Post postFindId(Long id);

    //단일 회원의 게시글 조회
    Page<PostOfMemberDTO> findPostAllByMemberId(Pageable pageable, Long id);
}
