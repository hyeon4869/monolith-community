package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.MemberDTO.MemberPasswordDTO;
import community.community.entity.Member;
import community.community.exception.customException.DBAccessException;
import community.community.exception.customException.NotFoundMemberException;
import community.community.repository.memberRepository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Qualifier("defaultUpdate")
public class MemberUpdateServiceImp implements MemberUpdateService {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private final PasswordValidator passwordValidator;

    public MemberUpdateServiceImp(MemberRepository memberRepository, PasswordValidator passwordValidator){
        this.memberRepository=memberRepository;
        this.passwordValidator=passwordValidator;
    }

    @Override
    @Transactional
    //비밀번호 변경 로직
    public MemberIdAndEmailDTO updatePassword(Long id, MemberPasswordDTO memberPasswordDTO){
        Member member=memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("회원 정보 변경을 위한 아이디 조회에 실패했습니다."));
        //비밀번호 변경 메서드
        try {
            member.updatePassword(memberPasswordDTO.getPassword());
            MemberIdAndEmailDTO updateDTO = MemberIdAndEmailDTO.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .build();
            return updateDTO;
        } catch (DataAccessException e) {
            throw new DBAccessException("비밀번호 업데이트 중 데이터베이스에 오류가 발생했습니다.",e);
        } catch (Exception e) {
            throw new RuntimeException("비밀번호 수정 중 예상치 못한 예외가 발생했습니다.");
        }
    }

    @Override
    @Transactional
    public void deleteMember(Long id, MemberPasswordDTO memberPasswordDTO){
        Member member=memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("삭제하려는 계정이 조회되지 않습니다."));

        try {
            passwordValidator.validate(memberPasswordDTO.getPassword(), member.getPassword(), "현재 비밀번화와 일치하지 않습니다.");
            memberRepository.deleteById(id);
        } catch (DataAccessException e){
            throw new DBAccessException("회원 삭제 중 데이터베이스에 오류가 발생했습니다.", e);
        } catch (Exception e){
            throw new RuntimeException("회원 삭제 중 예상치 못한 예외가 발생했습니다.");
        }
    }
}
