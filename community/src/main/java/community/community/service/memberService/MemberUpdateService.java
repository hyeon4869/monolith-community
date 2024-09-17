package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.dto.MemberDTO.MemberPasswordDTO;
import community.community.entity.Member;
import community.community.exception.customException.NotFoundMemberException;
import community.community.interfaceService.memberInterface.MemberUpdateInterface;
import community.community.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Qualifier("basicUpdate")
public class MemberUpdateService implements MemberUpdateInterface {

    private final MemberRepository memberRepository;

    public MemberUpdateService(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
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
    public Member updatePassword(Long id, MemberPasswordDTO memberPasswordDTO){
        Member member=memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundMemberException("회원 정보 변경을 위한 아이디 조회에 실패했습니다."));

        //비밀번호 변경 로직
        member.setPassword(memberPasswordDTO.getPassword());
        return member;
    }
}
