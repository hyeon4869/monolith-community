package community.community.service.commentService;

import community.community.dto.commentDTO.CommentViewDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.repository.commentRepository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CommentViewServiceImp implements CommentViewService{

    private final CommentRepository commentRepository;

    public CommentViewServiceImp(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }

    @Override
    public List<CommentViewDTO> commentView(Long id) {

        List<CommentViewDTO> commentViewDTOS = commentRepository.findReadAll(id).stream()
                //여기 수정하기
                 .map(CommentViewDTO::fromEntity)
                .toList();
        return commentViewDTOS;
    }

    @Override
    public List<RepliesCommentRegisterDTO> repliesCommentView(Long parentId){
        List<RepliesCommentRegisterDTO> repliesCommentRegisterDTOS = commentRepository.findRepliesReadAll(parentId).stream()
                .map(RepliesCommentRegisterDTO::fromEntity)
                .toList();
        return repliesCommentRegisterDTOS;
    }
}
