package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.entity.Member;
import community.community.exception.customException.DBAccessException;
import community.community.exception.customException.NotFoundMemberException;
import community.community.mapper.MemberMapper;
import community.community.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberFindServiceImp implements MemberFindService{

    private final MemberRepository memberRepository;


    @Override
    public MemberIdAndEmailDTO findMember(Long id){


        Member member =memberRepository.findByReadId(id)
                .orElseThrow(()-> new NotFoundMemberException("일치하는 회원이 존재하지 않습니다."));

        return MemberMapper.toMemberIdAndEmailDTO(member);
    }


    //회원 전체 조회

    @Override
    public List<MemberIdAndEmailDTO> findMemberList(){
        List<Member> memberList=memberRepository.findReadMemberList();

        return memberList.stream()
                .map(member -> {
                    try{
                       return new MemberIdAndEmailDTO(member.getId(), member.getEmail());
                    } catch (DataAccessException e){
                        throw new DBAccessException("회원 정보 조회 중 데이터베이스 문제가 발생했습니다.",e);
                    } catch (Exception e){
                        throw new NotFoundMemberException("회원 정보 조회 중 예상치 못한 문제가 발생했습니다");
                    }
                })
                .collect(Collectors.toList());
    }


    @Override
    public Member findByReadEmail(String email) {
        Member member =memberRepository.findByReadEmail(email)
                .orElseThrow(()->new IllegalArgumentException("확인되지 않은 회원입니다. : "+ email));
        return member;
    }

    @Override
    public Member findByEmail(String email) {
        Member member =memberRepository.findByEmail(email)
                .orElseThrow(()->new IllegalArgumentException("확인되지 않은 회원입니다. : "+ email));
        return member;
    }

}
