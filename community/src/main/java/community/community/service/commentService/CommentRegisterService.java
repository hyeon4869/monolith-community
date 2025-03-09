package community.community.service.commentService;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.dto.commentDTO.ReplicaCommentRegisterDTO;
import jakarta.servlet.http.HttpSession;

public interface CommentRegisterService {
    String commentRegister(Long id, CommentRegisterDTO commentRegisterDTO, HttpSession session);

    String replicaCommentRegister(Long commentId, ReplicaCommentRegisterDTO replicaCommentRegisterDTO, HttpSession session);
}
