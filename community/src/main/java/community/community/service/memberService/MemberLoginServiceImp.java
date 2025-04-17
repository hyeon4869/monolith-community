package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberLoginDTO;
import community.community.dto.MemberDTO.MemberSuccessLoginDTO;
import community.community.entity.Member;
import community.community.exception.customException.NotFoundMemberException;
import community.community.mapper.MemberMapper;
import community.community.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberLoginServiceImp implements MemberLoginService {

    private final MemberRepository memberRepository;
    private final PasswordValidator passwordValidator;

    @Override
    @Transactional
    public MemberSuccessLoginDTO memberLogin(MemberLoginDTO memberLoginDTO) {
        Member member = memberRepository.findByReadEmail(memberLoginDTO.getEmail())
                .orElseThrow(() -> new NotFoundMemberException("아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다."));

        //요청 스코프를 사용한 비밀번호 검증 로직
        passwordValidator.validate(memberLoginDTO.getPassword(), member.getPassword(), "아이디가 존재하지 않거나 비밀번호가 일치하지 않습니다.");

        return MemberMapper.toSuccessLoginDTO(member);
    }
}
