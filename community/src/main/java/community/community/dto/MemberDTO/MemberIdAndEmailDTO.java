package community.community.dto.MemberDTO;

import community.community.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberIdAndEmailDTO {

    private Long id;

    private String email;

    public static MemberIdAndEmailDTO toDTO(Member member){
        return MemberIdAndEmailDTO.builder()
                .email(member.getEmail())
                .id(member.getId())
                .build();
    }
}
