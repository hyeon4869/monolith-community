package community.community.service.postService;

import community.community.dto.postDTO.PostDTO;
import community.community.entity.Post;
import community.community.interfaceService.postInterface.PostRegistrationInterface;
import community.community.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostRegistrationService implements PostRegistrationInterface {

    private final PostRepository postRepository;

    public PostRegistrationService(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    public Post postRegistration(PostDTO postDTO){
        //게시물 등록 로직
        Post post = new Post();
        post.convertToPost(postDTO);
        postRepository.save(post);
        return post;
    }
}
