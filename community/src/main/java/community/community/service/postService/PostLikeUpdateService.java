package community.community.service.postService;

import community.community.repository.postRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostLikeUpdateService {

    private final PostRepository postRepository;


    @Transactional
    public void decreaseLikeCount(Long id){
        postRepository.decreaseLikeCount(id);
    }

    @Transactional
    public void increaseLikeCount(Long id){
        postRepository.increaseLikeCount(id);
    }

}
