package community.community.dto.MemberDTO;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDTO {

    private Long id;

    private String email;

    private String password;
}