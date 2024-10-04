package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.Post;
import community.community.interfaceService.postInterface.PostFindInterface;
import community.community.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostFindService implements PostFindInterface {

    private final PostRepository postRepository;

    public PostFindService(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    //게시물 메인 조회
    public List<PostFindDTO> postFindAll(){
        List<PostFindDTO> posts =postRepository.findAllRead();
        System.out.println(posts);
        List<PostFindDTO> postFindDTOS = new ArrayList<>();

//        for(Post post: posts){
//            PostFindDTO postFindDTO = new PostFindDTO();
//            postFindDTO.setId(post.getId());
//            postFindDTO.setTitle(post.getTitle());
//            postFindDTO.setMemberEmail(post.getMember().getEmail());
//            postFindDTOS.add(postFindDTO);
//        }
        return posts;
    }

    //게시물 상세 조회
    public PostDetailDTO postDetail(Long id){
        Post post =postRepository.findByReadId(id)
                .orElseThrow(()->new IllegalArgumentException("삭제된 게시물입니다."));

        PostDetailDTO postDetailDTO = new PostDetailDTO();
        postDetailDTO.setId(post.getId());
        postDetailDTO.setTitle(post.getTitle());
        postDetailDTO.setContent(post.getContent());
        return postDetailDTO;
    }

}
