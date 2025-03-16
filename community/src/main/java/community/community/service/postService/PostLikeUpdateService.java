package community.community.service.postService;

import community.community.repository.postRepository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostLikeUpdateService {

    private final PostRepository postRepository;

    public PostLikeUpdateService(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    @Transactional
    public void decreaseLikeCount(Long id){
        postRepository.decreaseLikeCount(id);
    }

    @Transactional
    public void increaseLikeCount(Long id){
        postRepository.increaseLikeCount(id);
    }

}
