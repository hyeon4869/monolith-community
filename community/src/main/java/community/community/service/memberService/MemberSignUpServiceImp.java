package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.entity.Member;
import community.community.exception.customException.DuplicateEmailException;
import community.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("basicSignUp")
@Transactional(readOnly = true)
public class MemberSignUpServiceImp implements MemberSignUpService {

    @Autowired
    private final MemberRepository memberRepository;

    public MemberSignUpServiceImp(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }

    @Override
    public String hello(){
        String hello = "hello";
        return hello;
    }

    @Override
    @Transactional
    public String signUp(MemberDTO memberDTO){
        //이메일 중복 검사

        validationDuplicateEmail(memberDTO.getEmail());

        if(memberDTO.getEmail()==null || memberDTO.getPassword()==null){
            throw new IllegalArgumentException("아이디나 비밀번호는 필수입니다.");
        }
        Member member = Member.builder()
                .email(memberDTO.getEmail())
                .password(memberDTO.getPassword())
                .build();
        memberRepository.save(member);

        return String.valueOf(member.getId());
    }

    @Override
    public void validationDuplicateEmail(String email){
        if(memberRepository.findByEmail(email).isPresent()){
            throw new DuplicateEmailException("이미 사용중인 이메일입니다.");
        }
    }
}
