package community.community.interfaceService.memberInterface;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.entity.Member;

public interface MemberLoginInterface {
    Member memberLogin(MemberLoginDTO memberLoginDTO);
}
