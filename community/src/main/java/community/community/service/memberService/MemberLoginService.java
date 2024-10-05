package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.entity.Member;

public interface MemberLoginService {
    Member memberLogin(MemberLoginDTO memberLoginDTO);
}
