package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.MemberDTO.MemberMyPageDTO;
import community.community.entity.Member;

import java.util.List;

public interface MemberFindService {
    MemberIdAndEmailDTO findMember(Long id);

    List<MemberIdAndEmailDTO> findMemberList();

    MemberMyPageDTO findMemberAndPost(Long id);

    //좋아요 기능에 사용하는 email 조회
    Member findByEmail(String email);
}
