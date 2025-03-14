package community.community.service.commentService;

import community.community.dto.commentDTO.CommentViewDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;

import java.util.List;

public interface CommentViewService {

    List<CommentViewDTO> commentView(Long id);

    List<RepliesCommentRegisterDTO> repliesCommentView(Long parentId);
}
