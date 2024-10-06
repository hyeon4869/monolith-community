package community.community.dto.MemberDTO;

import community.community.dto.postDTO.PostFindDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberMyPageDTO {

    private Long id;

    private String email;

    private List<PostFindDTO> postList = new ArrayList<>();
}
