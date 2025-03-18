package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;

public interface MemberSignUpService {
    //연결 테스트

    //회원가입 로직
    String signUp(MemberDTO memberDTO);

    //아이디 중복 검사
    void validationDuplicateEmail(String email);

}
