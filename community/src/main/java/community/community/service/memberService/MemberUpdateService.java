package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.MemberDTO.MemberPasswordDTO;

public interface MemberUpdateService {

    MemberIdAndEmailDTO updatePassword(Long id, MemberPasswordDTO memberPasswordDTO);

    void deleteMember(Long id, MemberPasswordDTO memberPasswordDTO);
}
