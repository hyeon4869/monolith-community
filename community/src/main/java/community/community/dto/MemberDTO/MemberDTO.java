package community.community.dto.MemberDTO;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {

    private Long id;

    private String email;

    private String password;
}
