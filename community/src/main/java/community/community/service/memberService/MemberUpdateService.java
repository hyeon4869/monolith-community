package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberPasswordDTO;
import community.community.entity.Member;

public interface MemberUpdateService {

    Member updatePassword(Long id, MemberPasswordDTO memberPasswordDTO);

    void deleteMember(Long id, MemberPasswordDTO memberPasswordDTO);
}
