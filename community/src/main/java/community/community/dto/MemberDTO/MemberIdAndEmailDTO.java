package community.community.dto.MemberDTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MemberIdAndEmailDTO {

    private Long id;

    private String email;
}
