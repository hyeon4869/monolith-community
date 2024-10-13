package community.community.service.memberService;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.dto.MemberDTO.MemberMyPageDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.Member;
import community.community.exception.customException.DBAccessException;
import community.community.exception.customException.NotFoundMemberException;
import community.community.exception.customException.NotFoundPostException;
import community.community.repository.memberRepository.MemberRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@Qualifier("defaultFind")
public class MemberFindServiceImp implements MemberFindService{

    private final MemberRepository memberRepository;

    public MemberFindServiceImp(MemberRepository memberRepository){
        this.memberRepository= memberRepository;
    }

    @Override
    public MemberDTO findMember(Long id){


        Member member =memberRepository.findByReadId(id)
                .orElseThrow(()-> new NotFoundMemberException("일치하는 회원이 존재하지 않습니다."));


        MemberDTO memberDTO = MemberDTO.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build();

        return memberDTO;
    }

    @Override
    public List<MemberDTO> findMemberList(){
        List<Member> memberList=memberRepository.findReadMemberList();

        return memberList.stream()
                .map(member -> {
                    try{
                       return new MemberDTO(member.getId(), member.getEmail(), member.getPassword());
                    } catch (DataAccessException e){
                        throw new DBAccessException("회원 정보 조회중 데이터베이스 문제가 발생했습니다.",e);
                    } catch (Exception e){
                        throw new NotFoundMemberException("회원 정보 조회 중 예상치 못한 문제가 발생했습니다");
                    }
                })
                .collect(Collectors.toList());
    }

    @Override
    public MemberMyPageDTO findMemberAndPost(Long id) {
        Member member=memberRepository.findByMemberAndPost(id);

        List<PostFindDTO> postFindDTOList = member.getPostList()==null || member.getPostList().isEmpty()
                ? new ArrayList<>(): member.getPostList().stream()
                .map( post -> {
                    try{
                        return new PostFindDTO(post.getId(), post.getTitle(), post.getMember().getEmail());
                    } catch(Exception e){
                        throw new NotFoundPostException("게시물을 불러오던 중 문제가 발생했습니다.", e);
                    }
                })
                .toList();

        MemberMyPageDTO memberMyPageDTO = new MemberMyPageDTO();
        memberMyPageDTO.setId(member.getId());
        memberMyPageDTO.setEmail(member.getEmail());
        memberMyPageDTO.setPostList(postFindDTOList);
        return memberMyPageDTO;
    }

}
