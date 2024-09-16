package community.community.interfaceService.memberInterface;

import community.community.dto.MemberDTO.MemberDTO;

public interface MemberSignUpInterface {

    //연결 테스트
    String hello();

    //회원가입 로직
    String signUp(MemberDTO memberDTO);

    //아이디 중복 검사
    void validationDuplicateEmail(String email);
}
