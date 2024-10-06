package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.Post;
import community.community.exception.customException.NotFoundMemberException;
import community.community.exception.customException.NotFoundPostException;
import community.community.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PostFindServiceImp implements PostFindService {

    private final PostRepository postRepository;

    public PostFindServiceImp(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    //게시물 메인 조회
    public List<PostFindDTO> postFindAll(){
        List<Post> postList =postRepository.findAllRead();


        return postList==null || postList.isEmpty() ? new ArrayList<>() : postList.stream()
                .map(post ->{
                    try{
                        if(post.getMember()==null) {
                            throw new NotFoundMemberException("게시물을 작성한 작성자의 정보가 조회되지 않습니다");
                        }
                        return new PostFindDTO(post.getId(), post.getTitle(), post.getMember().getEmail());
                    } catch (NullPointerException e) {
                        throw new NotFoundPostException("게시물을 불러오던 중 문제가 발생했습니다.", e);
                    } catch (Exception e){
                        throw new NotFoundPostException("게시물을 불러오던 중 문제가 발생했습니다.", e);
                    }

                })
                .toList();
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
