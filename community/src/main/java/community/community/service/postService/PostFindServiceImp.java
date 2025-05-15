package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindAllDTO;
import community.community.dto.postDTO.PostOfMemberDTO;
import community.community.entity.Post;
import community.community.entity.PostFile;
import community.community.exception.customException.DBAccessException;
import community.community.mapper.PostMapper;
import community.community.repository.postRepository.PostFileRepository;
import community.community.repository.postRepository.PostRepository;
import community.community.service.commentService.CommentFindService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostFindServiceImp implements PostFindService {

    private final PostRepository postRepository;
    private final CommentFindService commentFindService;
    private final PostFileRepository postFileRepository;
    private final PostPopularFindService postPopularFindService;
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

    //단일 회원의 게시물 조회(마이페이지)
    @Override
    public Page<PostOfMemberDTO> findPostAllByMemberId(Pageable pageable, Long id){
        try {
            Page<PostOfMemberDTO> postOfMemberDTOS = postRepository.findAllPostByMemberId(pageable, id);
            return postOfMemberDTOS;
        } catch (DataAccessException e) {
            throw new DBAccessException("데이터베이스 접근에 문제가 발생했습니다.",e);
        }
    }


    //게시물 상세 조회
    @Override
    public PostDetailDTO postDetail(HttpSession session, Long id){
        Map<String, Object> postDTO = new HashMap<>();

        Post post =postRepository.findByReadId(id)
                .orElseThrow(()->new IllegalArgumentException("삭제된 게시물입니다."));

        PostFile postFile = postFileRepository.findReadPostId(id);

        postPopularFindService.addRecentPosts(session, id);

        return PostMapper.toPostDetailDTO(post, postFile);
    }

    @Override
    public Post postFindId(Long id) {

        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("삭제된 게시물입니다."));
        return post;
    }


}
