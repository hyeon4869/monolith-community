package community.community.service.commentService;

import community.community.dto.commentDTO.CommentViewDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;

import java.util.List;

public interface CommentFindService {

    //댓글 전체 조회
    List<CommentViewDTO> findComment(Long id);

    //대댓글 전체 조회
    List<RepliesCommentRegisterDTO> findRepliesComment(Long parentId);

    Comment findId(Long id);
}
