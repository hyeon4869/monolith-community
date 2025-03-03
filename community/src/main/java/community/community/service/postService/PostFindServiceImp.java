package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.Post;
import community.community.repository.postRepository.PostRepository;
import community.community.service.commentService.CommentViewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostFindServiceImp implements PostFindService {

    @Autowired
    private final PostRepository postRepository;
    @Autowired
    private final CommentViewService commentViewService;

    public PostFindServiceImp(PostRepository postRepository, CommentViewService commentViewService){
        this.postRepository=postRepository;
        this.commentViewService=commentViewService;
    }

    //게시물 메인 조회
    @Override
    public Page<PostFindDTO> postFindAll(Pageable pageable) {
        Page<PostFindDTO> postPage = postRepository.findAllPostWithEmail(pageable);

        return postPage;
    }

    //게시물 상세 조회
    @Override
    public PostDetailDTO postDetail(Long id){
        Post post =postRepository.findByReadId(id)
                .orElseThrow(()->new IllegalArgumentException("삭제된 게시물입니다."));

        PostDetailDTO postDetailDTO =PostDetailDTO.toFromEntity(post);
        return postDetailDTO;
    }

    @Override
    public Post postFindId(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("삭제된 게시물입니다."));
        return post;
    }


}
