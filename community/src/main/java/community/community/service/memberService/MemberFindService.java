package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberFindDTO;
import community.community.dto.MemberDTO.MemberMyPageDTO;

import java.util.List;

public interface MemberFindService {
    MemberFindDTO findMember(Long id);

    List<MemberFindDTO> findMemberList();

    MemberMyPageDTO findMemberAndPost(Long id);
}
