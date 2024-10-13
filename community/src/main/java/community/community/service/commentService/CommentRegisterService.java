package community.community.service.commentService;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.entity.Comment;

public interface CommentRegisterService {
    Comment register(CommentRegisterDTO commentRegisterDTO, String writer, Long id);

}
