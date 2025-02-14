package community.community.service.commentService;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.entity.Comment;
import community.community.repository.commentRepository.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CommentRegisterServiceImp implements CommentRegisterService{

    private final CommentRepository commentRepository;

    public CommentRegisterServiceImp(CommentRepository commentRepository){
        this.commentRepository=commentRepository;
    }


    @Override
    public void commentRegister(CommentRegisterDTO commentRegisterDTO) {

        Comment comment = Comment.builder()
                .content(commentRegisterDTO.getContent())
                .build();
        commentRepository.save(comment);


    }
}
