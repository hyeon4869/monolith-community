package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindAllDTO;
import community.community.entity.Post;
import community.community.exception.customException.DBAccessException;
import community.community.repository.postRepository.PostRepository;
import community.community.service.commentService.CommentViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostFindServiceImp implements PostFindService {

    private final PostRepository postRepository;
    private final CommentViewService commentViewService;


    //좋아요 수가 추가된 메인 조회
    @Override
    public Page<PostFindAllDTO> postFindAll(Pageable pageable) {
        try{
            Page<PostFindAllDTO> postPage = postRepository.findAllPostWithEmailWithLike(pageable);
            return postPage;
        } catch (DataAccessException e) {
            throw new DBAccessException("데이터베이스 접근에 문제가 발생했습니다.",e);
        }

    }


    //게시물 상세 조회
    @Override
    public PostDetailDTO postDetail(Long id){
        Post post =postRepository.findByReadId(id)
                .orElseThrow(()->new IllegalArgumentException("삭제된 게시물입니다."));

        PostDetailDTO postDetailDTO =PostDetailDTO.toDTO(post);
        return postDetailDTO;
    }

    @Override
    public Post postFindId(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("삭제된 게시물입니다."));
        return post;
    }


}
