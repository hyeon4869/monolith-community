package community.community.dto.MemberDTO;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberIdAndEmailDTO {

    private Long id;
    private String email;
}
