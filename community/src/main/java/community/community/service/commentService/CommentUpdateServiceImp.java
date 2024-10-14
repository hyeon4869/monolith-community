package community.community.service.commentService;

import community.community.dto.commentDTO.CommentUpdateDTO;
import community.community.entity.Comment;
import community.community.exception.customException.NotFoundCommentException;
import community.community.repository.commentRepository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentUpdateServiceImp implements CommentUpdateService{

    private final CommentRepository commentRepository;

    public CommentUpdateServiceImp(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }


    @Transactional
    @Override
    public CommentUpdateDTO findCommentId(Long id, CommentUpdateDTO commentUpdateDTO) {

        Comment comment=commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundCommentException("존재하지 않는 댓글입니다."));

        comment.setContent(commentUpdateDTO.getContent());

        CommentUpdateDTO commentUpdateDTO1= new CommentUpdateDTO();
        commentUpdateDTO1.setContent(comment.getContent());
        return commentUpdateDTO1;
    }
}
