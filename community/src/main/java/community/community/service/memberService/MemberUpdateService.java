package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.dto.MemberDTO.MemberPasswordDTO;
import community.community.entity.Member;

public interface MemberUpdateService {
    MemberDTO findMember(Long id);

    Member updatePassword(Long id, MemberPasswordDTO memberPasswordDTO);

    void deleteMember(Long id, MemberPasswordDTO memberPasswordDTO);
}
