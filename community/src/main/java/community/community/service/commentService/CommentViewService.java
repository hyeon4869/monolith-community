package community.community.service.commentService;

import community.community.dto.commentDTO.CommentViewDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;

import java.util.List;

public interface CommentViewService {

    //댓글 전체 조회
    List<CommentViewDTO> commentView(Long id);

    //대댓글 전체 조회
    List<RepliesCommentRegisterDTO> repliesCommentView(Long parentId);

    Comment findId(Long id);
}
