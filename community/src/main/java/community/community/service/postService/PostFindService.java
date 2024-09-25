package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
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
        List<PostRepository.PostTitleAndMember> posts =postRepository.findAllBy();
        List<PostFindDTO> postFindDTOS = new ArrayList<>();

        for(PostRepository.PostTitleAndMember post: posts){
            PostFindDTO postFindDTO = new PostFindDTO();
            postFindDTO.setTitle(post.getTitle());
            postFindDTO.setMemberEmail(post.getMemberEmail());
            postFindDTOS.add(postFindDTO);
        }
        return postFindDTOS;
    }
}
