package community.community.dto.MemberDTO;

import community.community.entity.Member;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberSuccessLoginDTO {

    private Long id;

    private String email;

    public static MemberSuccessLoginDTO toDTO(Member member){
        return  MemberSuccessLoginDTO.builder()
                .id(member.getId())
                .email(member.getEmail())
                .build();
    }
}
