package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.entity.Member;
import community.community.exception.customException.DBAccessException;
import community.community.exception.customException.InvalidEmailException;
import community.community.exception.customException.SignUpFailedException;
import community.community.mapper.MemberMapper;
import community.community.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberSignUpServiceImp implements MemberSignUpService {

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public String signUp(MemberDTO memberDTO){
        //이메일 중복 검사

        validationDuplicateEmail(memberDTO.getEmail());

        if(memberDTO.getEmail()==null || memberDTO.getPassword()==null){
            throw new IllegalArgumentException("아이디나 비밀번호는 필수입니다.");
        }
        try {
            Member member = MemberMapper.toEntity(memberDTO);
            memberRepository.save(member);

            return String.valueOf(member.getId());

        } catch (DataAccessException e) {
            throw new DBAccessException("데이터베이스의 오류로 회원가입에 실패했습니다", e);
        } catch (Exception e){
            throw new SignUpFailedException("서버의 오류로 회원가입에 실패했습니다.", e);
        }

    }

    @Override
    public void validationDuplicateEmail(String email){
        if(memberRepository.findByReadEmail(email).isPresent()){
            throw new InvalidEmailException("이미 사용중인 이메일입니다.");
        }
    }
}
