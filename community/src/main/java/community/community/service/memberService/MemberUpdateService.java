package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
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
    public MemberDTO findMember(String email){
        Member member =memberRepository.findByEmail(email)
                .orElseThrow(()-> new NotFoundMemberException("일치하는 회원이 존재하지 않습니다."));

        MemberDTO memberDTO = MemberDTO.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build();

        return memberDTO;
    }

//    @Override
//    @Transactional
//    public MemberDTO updatePassword(String password){
//
//        return
//    }
}
