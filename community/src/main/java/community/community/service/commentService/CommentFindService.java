package community.community.service.commentService;

import community.community.dto.commentDTO.CommentOfMemberDTO;
import community.community.dto.commentDTO.CommentFindDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentFindService {

    //댓글 전체 조회
    List<CommentFindDTO> findComment(Long id);

    //대댓글 전체 조회
    List<RepliesCommentRegisterDTO> findRepliesComment(Long parentId);

    Comment findId(Long id);

    //단일 회원 댓글 전부 조회
    Page<CommentOfMemberDTO> findAllCommentByMemberId(Pageable pageable, Long id);
}
