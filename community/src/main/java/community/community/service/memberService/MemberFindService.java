package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.dto.MemberDTO.MemberMyPageDTO;

import java.util.List;

public interface MemberFindService {
    MemberDTO findMember(Long id);

    List<MemberDTO> findMemberList();

    MemberMyPageDTO findMemberAndPost(Long id);
}
