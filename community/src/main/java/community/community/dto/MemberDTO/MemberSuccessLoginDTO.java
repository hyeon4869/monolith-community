package community.community.dto.MemberDTO;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberSuccessLoginDTO {

    private Long id;

    private String email;

}
