package community.community.interfaceService.memberInterface;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.dto.MemberDTO.MemberPasswordDTO;
import community.community.entity.Member;

public interface MemberUpdateInterface {
    MemberDTO findMember(Long id);

    Member updatePassword(Long id, MemberPasswordDTO memberPasswordDTO);

    void deleteMember(Long id);
}
