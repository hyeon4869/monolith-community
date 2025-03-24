package community.community.service.commentService;

import community.community.repository.commentRepository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentLikeUpdateService {

    private final CommentRepository commentRepository;

    @Transactional
    public void increaseLikeCount(Long id){
        commentRepository.increaseLikeCount(id);
    }

    @Transactional
    public void decreaseLikeCount(Long id){
        commentRepository.decreaseLikeCount(id);
    }
}
