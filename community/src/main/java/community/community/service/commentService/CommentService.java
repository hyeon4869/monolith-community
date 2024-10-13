package community.community.service.commentService;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.entity.Comment;

public interface CommentService{
    Comment register(CommentRegisterDTO commentRegisterDTO, String writer, Long id);

}
