package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.entity.Member;
import community.community.exception.customException.DBAccessException;
import community.community.exception.customException.InvalidEmailException;
import community.community.exception.customException.SignUpFailedException;
import community.community.interfaceService.memberInterface.MemberSignUpInterface;
import community.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Qualifier("basicSignUp")
@Transactional(readOnly = true)
public class MemberSignUpService implements MemberSignUpInterface {

    @Autowired
    private final MemberRepository memberRepository;

    public MemberSignUpService(MemberRepository memberRepository){
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
        try {
            Member member = Member.builder()
                    .email(memberDTO.getEmail())
                    .password(memberDTO.getPassword())
                    .build();
            memberRepository.save(member);

            return String.valueOf(member.getId());

        } catch (DataAccessException e) {
            throw new DBAccessException("서버의 오류로 회원가입에 실패했습니다", e);
        } catch (Exception e){
            throw new SignUpFailedException("서버의 오류로 회원가입에 실패했습니다.", e);
        }

    }

    @Override
    public void validationDuplicateEmail(String email){
        if(email==null||email.trim().isEmpty()){
            throw new IllegalArgumentException("조회할 이메일이 null 입니다.");
        }
        if(memberRepository.findByReadEmail(email).isPresent()){
            throw new InvalidEmailException("이미 사용중인 이메일입니다.");
        }
    }
}
