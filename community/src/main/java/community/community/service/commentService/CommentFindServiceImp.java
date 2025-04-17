package community.community.service.commentService;

import community.community.dto.commentDTO.CommentOfMemberDTO;
import community.community.dto.commentDTO.CommentFindDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;
import community.community.exception.customException.DBAccessException;
import community.community.mapper.CommentMapper;
import community.community.repository.commentRepository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentFindServiceImp implements CommentFindService {

    private final CommentRepository commentRepository;

    //댓글 전체 조회
    @Override
    public List<CommentFindDTO> findComment(Long id) {

        List<CommentFindDTO> commentFindDTOS = commentRepository.findReadAll(id).stream()
                //여기 수정하기
                 .map(CommentMapper::toCommentViewDTO)
                .toList();
        return commentFindDTOS;
    }

    //대댓글 전체 조회
    @Override
    public List<RepliesCommentRegisterDTO> findRepliesComment(Long parentId){
        List<RepliesCommentRegisterDTO> repliesCommentRegisterDTOS = commentRepository.findRepliesReadAll(parentId).stream()
                .map(CommentMapper::toRepliesCommentRegisterDTO)
                .toList();
        return repliesCommentRegisterDTOS;
    }

    @Override
    public Comment findId(Long id){
        Comment comment =commentRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 댓글입니다.")
        );
        return comment;
    }

    @Override
    public Page<CommentOfMemberDTO> findAllCommentByMemberId(Pageable pageable, Long id) {
        try{
           Page<CommentOfMemberDTO> commentOfMemberDTO= commentRepository.findAllCommentByMemberId(pageable, id);
            return commentOfMemberDTO;
        } catch (DataAccessException e) {
            throw new DBAccessException("데이터베이스에 문제가 발생하여 회원의 댓글을 조회할 수 없습니다.", e);
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 문제가 발생했습니다.");
        }
    }
}
