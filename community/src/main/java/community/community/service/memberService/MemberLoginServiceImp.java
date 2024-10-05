package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.entity.Member;
import community.community.exception.customException.NotFoundMemberException;
import community.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("basicLogin")
public class MemberLoginServiceImp implements MemberLoginService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final PasswordValidator passwordValidator;

    public MemberLoginServiceImp(MemberRepository memberRepository, PasswordValidator passwordValidator){
        this.memberRepository=memberRepository;
        this.passwordValidator=passwordValidator;
    }

    @Override
    @Transactional
    public Member memberLogin(MemberLoginDTO memberLoginDTO) {
        Member member = memberRepository.findByEmail(memberLoginDTO.getEmail())
                .orElseThrow(() -> new NotFoundMemberException("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다."));

        //요청 스코프를 사용한 비밀번호 검증 로직
        passwordValidator.validate(memberLoginDTO.getPassword(), member.getPassword(), "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.");

        return member;
    }
}
