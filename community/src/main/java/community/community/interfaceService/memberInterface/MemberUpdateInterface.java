package community.community.interfaceService.memberInterface;

import community.community.dto.MemberDTO.MemberDTO;

public interface MemberUpdateInterface {
    MemberDTO findMember(String email);

  //  MemberDTO updatePassword(String password);
}
