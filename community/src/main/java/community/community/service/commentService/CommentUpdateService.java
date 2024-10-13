package community.community.service.commentService;

import community.community.dto.commentDTO.CommentUpdateDTO;

public interface CommentUpdateService {
    CommentUpdateDTO findCommentId(Long id, CommentUpdateDTO commentUpdateDTO);
}
