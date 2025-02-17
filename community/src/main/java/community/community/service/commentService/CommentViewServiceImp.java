package community.community.service.commentService;

import community.community.dto.commentDTO.CommentViewDTO;
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
    @Transactional
    public List<CommentViewDTO> commentView(Long id) {

        List<CommentViewDTO> commentViewDTOS = commentRepository.findReadAll(id).stream()
                //여기 수정하기
                 .map(CommentViewDTO::toFromEntity)
                .toList();
        return commentViewDTOS;
    }
}
