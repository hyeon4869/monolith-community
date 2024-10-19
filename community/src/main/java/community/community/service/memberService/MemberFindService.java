package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.MemberDTO.MemberMyPageDTO;

import java.util.List;

public interface MemberFindService {
    MemberIdAndEmailDTO findMember(Long id);

    List<MemberIdAndEmailDTO> findMemberList();

    MemberMyPageDTO findMemberAndPost(Long id);
}
