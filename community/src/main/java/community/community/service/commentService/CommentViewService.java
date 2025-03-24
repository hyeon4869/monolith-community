package community.community.service.commentService;

import community.community.dto.commentDTO.CommentViewDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;

import java.util.List;

public interface CommentViewService {

    List<CommentViewDTO> commentView(Long id);

    List<RepliesCommentRegisterDTO> repliesCommentView(Long parentId);

    Comment findId(Long id);
}
