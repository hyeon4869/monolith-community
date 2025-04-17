package community.community.mapper;

import community.community.dto.MemberDTO.MemberDTO;
import community.community.dto.MemberDTO.MemberIdAndEmailDTO;
import community.community.dto.MemberDTO.MemberSuccessLoginDTO;
import community.community.entity.Member;

public class MemberMapper {

    public static MemberSuccessLoginDTO toSuccessLoginDTO(Member member){
        return  MemberSuccessLoginDTO.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .build();
    }

    public static MemberIdAndEmailDTO toMemberIdAndEmailDTO(Member member) {
        return MemberIdAndEmailDTO.builder()
                    .id(member.getId())
                    .email(member.getEmail())
                    .build();
    }

    //////////////////////////////////////////////
    //엔티티로 변환하는 로직


    //memberDTO
    public static Member toEntity(MemberDTO memberDTO){
        return Member.builder()
                    .email(memberDTO.getEmail())
                    .password(memberDTO.getPassword())
                    .build();
    }

    public static Member toEntity(String writer) {
        return Member.builder()
                .email(writer)
                .build();
    }

}
