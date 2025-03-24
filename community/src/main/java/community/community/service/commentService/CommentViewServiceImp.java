package community.community.service.commentService;

import community.community.dto.commentDTO.CommentViewDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;
import community.community.repository.commentRepository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentViewServiceImp implements CommentViewService{

    private final CommentRepository commentRepository;

    @Override
    public List<CommentViewDTO> commentView(Long id) {

        List<CommentViewDTO> commentViewDTOS = commentRepository.findReadAll(id).stream()
                //여기 수정하기
                 .map(CommentViewDTO::toDTO)
                .toList();
        return commentViewDTOS;
    }

    @Override
    public List<RepliesCommentRegisterDTO> repliesCommentView(Long parentId){
        List<RepliesCommentRegisterDTO> repliesCommentRegisterDTOS = commentRepository.findRepliesReadAll(parentId).stream()
                .map(RepliesCommentRegisterDTO::toDTO)
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
}
