package community.community.service.myPageService;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.commentDTO.CommentOfMemberDTO;
import community.community.dto.postDTO.PostOfMemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MyPageService {
    //회원 정보 불러오기
    MemberIdAndEmailDTO findMember(Long id);

    //id에 해당하는 게시글 조회
    Page<PostOfMemberDTO> findPostByMemberId(Pageable pageable, Long id);

    //id에 해당하는 댓글 조회
    Page<CommentOfMemberDTO> findCommentByMemberId(Pageable pageable, Long id);
}
