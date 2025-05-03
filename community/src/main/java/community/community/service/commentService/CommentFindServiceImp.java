package community.community.service.commentService;

import community.community.dto.commentDTO.CommentFindDTO;
import community.community.dto.commentDTO.CommentOfMemberDTO;
import community.community.dto.commentDTO.CursorResult;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;
import community.community.exception.customException.DBAccessException;
import community.community.mapper.CommentMapper;
import community.community.repository.commentRepository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public CursorResult<CommentFindDTO> findComment(Long id, Long cursor, int size) {
        List<Comment> comments;

        // 커서 값이 없는 경우(첫 페이지)
        if (cursor == null) {
            comments = commentRepository.findFirstPage(id, PageRequest.of(0, size + 1));
        } else {
            comments = commentRepository.findNextPage(id, cursor, PageRequest.of(0, size + 1));
        }

        // DTO 변환
        List<CommentFindDTO> commentFindDTOS = comments.stream()
                .map(CommentMapper::toCommentViewDTO)
                .toList();

        // 다음 페이지가 있는지 확인
        boolean hasNext = commentFindDTOS.size() > size;
        if (hasNext) {
            commentFindDTOS = commentFindDTOS.subList(0, size);
        }
        Long nextCursor = hasNext && !commentFindDTOS.isEmpty()
                ? commentFindDTOS.get(commentFindDTOS.size() - 1).getId()
                : null;

        return new CursorResult<>(commentFindDTOS, hasNext, nextCursor);
    }

    //대댓글 전체 조회
    @Override
    public CursorResult<RepliesCommentRegisterDTO> findRepliesComment(Long parentId, Long cursor, int size){
        List<Comment> replies;

        // 커서 값이 없는 경우(첫 페이지)
        if (cursor == null) {
            replies = commentRepository.findRepliesFirstPage(parentId, PageRequest.of(0, size + 1));
        } else {
            replies = commentRepository.findRepliesNextPage(parentId, cursor, PageRequest.of(0, size + 1));
        }

        // DTO 변환
        List<RepliesCommentRegisterDTO> repliesCommentRegisterDTOS = replies.stream()
                .map(CommentMapper::toRepliesCommentRegisterDTO)
                .toList();

        // 다음 페이지가 있는지 확인
        boolean hasNext = repliesCommentRegisterDTOS.size() > size;
        if (hasNext) {
            repliesCommentRegisterDTOS = repliesCommentRegisterDTOS.subList(0, size);
        }
        Long nextCursor = hasNext && !repliesCommentRegisterDTOS.isEmpty()
                ? repliesCommentRegisterDTOS.get(repliesCommentRegisterDTOS.size() - 1).getId()
                : null;

        return new CursorResult<>(repliesCommentRegisterDTOS, hasNext, nextCursor);
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
