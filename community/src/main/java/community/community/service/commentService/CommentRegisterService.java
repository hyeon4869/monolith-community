package community.community.service.commentService;

import community.community.dto.commentDTO.CommentRegisterDTO;
import jakarta.servlet.http.HttpSession;

public interface CommentRegisterService {
    String commentRegister(Long id, CommentRegisterDTO commentRegisterDTO, HttpSession session);
}
