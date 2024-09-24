package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.dto.MemberDTO.MemberPasswordDTO;
import community.community.entity.Member;
import community.community.exception.customException.NotFoundMemberException;
import community.community.interfaceService.memberInterface.MemberUpdateInterface;
import community.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Qualifier("basicUpdate")
public class MemberUpdateService implements MemberUpdateInterface {

    @Autowired
    private final MemberRepository memberRepository;

    @Autowired
    private PasswordValidator passwordValidator;

    public MemberUpdateService(MemberRepository memberRepository, PasswordValidator passwordValidator){
        this.memberRepository=memberRepository;
        this.passwordValidator=passwordValidator;
    }

    @Override
    public MemberDTO findMember(Long id){
        Member member =memberRepository.findById(id)
                .orElseThrow(()-> new NotFoundMemberException("일치하는 회원이 존재하지 않습니다."));

        MemberDTO memberDTO = MemberDTO.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build();

        return memberDTO;
    }

    @Override
    @Transactional
    //비밀번호 변경 로직
    public Member updatePassword(Long id, MemberPasswordDTO memberPasswordDTO){
        Member member=memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("회원 정보 변경을 위한 아이디 조회에 실패했습니다."));

        //비밀번호 변경 메서드
        member.setPassword(memberPasswordDTO.getPassword());
        return member;
    }

    @Override
    @Transactional
    public void deleteMember(Long id, MemberPasswordDTO memberPasswordDTO){
        Member member=memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("삭제하려는 계정이 조회되지 않습니다."));

        passwordValidator.validate(memberPasswordDTO.getPassword(), member.getPassword(), "현재 비밀번화와 일치하지 않습니다.");
        memberRepository.deleteById(id);
    }
}
