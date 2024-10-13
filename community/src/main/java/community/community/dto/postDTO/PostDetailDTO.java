package community.community.dto.postDTO;

import community.community.entity.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PostDetailDTO {

    private Long id;

    private String title;

    private String content;

    private List<Comment> commentList=new ArrayList<>();

}
