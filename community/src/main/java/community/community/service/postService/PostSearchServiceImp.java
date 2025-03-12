package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import community.community.repository.postRepository.PostRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Qualifier("BASIC SEARCH")
public class PostSearchServiceImp implements PostSearchService{

    private final PostRepository postRepository;

    public PostSearchServiceImp(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    @Override
    @Cacheable(value = "myC")
    public Page<PostFindDTO> postSearch(Pageable pageable, String title) {
        Page<PostFindDTO> searchTitle = postRepository.findSearchTitle(pageable, title);

        return searchTitle;
    }
}
