package community.community.service.commentService;

import community.community.dto.commentDTO.CommentViewDTO;

import java.util.List;

public interface CommentViewService {

    List<CommentViewDTO> commentView(Long id);
}
