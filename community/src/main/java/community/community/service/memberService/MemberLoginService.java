package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.dto.MemberDTO.MemberSuccessLoginDTO;

public interface MemberLoginService {
    MemberSuccessLoginDTO memberLogin(MemberLoginDTO memberLoginDTO);
}
