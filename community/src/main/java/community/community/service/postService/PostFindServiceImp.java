package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.Post;
import community.community.repository.postRepository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostFindServiceImp implements PostFindService {

    private final PostRepository postRepository;

    public PostFindServiceImp(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    //게시물 메인 조회
    public Page<PostFindDTO> postFindAll(Pageable pageable) {
        Page<PostFindDTO> postPage = postRepository.findAllPostWithEmail(pageable);

        return postPage;
    }

    //게시물 상세 조회
    public PostDetailDTO postDetail(Long id){
        Post post =postRepository.findByReadId(id)
                .orElseThrow(()->new IllegalArgumentException("삭제된 게시물입니다."));

        PostDetailDTO postDetailDTO =PostDetailDTO.toFromEntity(post);
        return postDetailDTO;
    }


}
