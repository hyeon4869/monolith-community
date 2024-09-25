package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.Post;
import community.community.interfaceService.postInterface.PostFindInterface;
import community.community.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostFindService implements PostFindInterface {

    private final PostRepository postRepository;

    public PostFindService(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    public List<PostFindDTO> postFindAll(){
        List<Post> posts =postRepository.findAll();
        List<PostFindDTO> postFindDTOS = new ArrayList<>();

        for(Post post: posts){
            PostFindDTO postFindDTO = new PostFindDTO();
            postFindDTO.setId(post.getId());
            postFindDTO.setTitle(post.getTitle());
            postFindDTO.setContent(post.getContent());
            postFindDTOS.add(postFindDTO);
        }
        return postFindDTOS;
    }
}
