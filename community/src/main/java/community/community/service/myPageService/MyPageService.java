package community.community.service.myPageService;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.postDTO.PostOfMemberDTO;
import community.community.service.commentService.CommentFindService;
import community.community.service.memberService.MemberFindService;
import community.community.service.postService.PostFindService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberFindService memberFindService;
    private final PostFindService postFindService;
    private final CommentFindService commentFindService;

    //회원 정보 불러오기
    public MemberIdAndEmailDTO findMember(Long id) {
        MemberIdAndEmailDTO memberIdAndEmailDTO = memberFindService.findMember(id);
        return memberIdAndEmailDTO;
    }


    public Page<PostOfMemberDTO> findPostByMemberId(Pageable pageable, Long id) {
        return postFindService.findPostAllByMemberId(pageable, id);
    }
}
