package community.community.service.postService;

import community.community.dto.postDTO.PostDetailDTO;
import community.community.dto.postDTO.PostFindDTO;
import community.community.entity.Post;
import community.community.exception.customException.NotFoundPostException;
import community.community.interfaceService.postInterface.PostFindInterface;
import community.community.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class PostFindService implements PostFindInterface {

    private final PostRepository postRepository;

    public PostFindService(PostRepository postRepository){
        this.postRepository=postRepository;
    }

    //게시물 메인 조회
    public List<PostFindDTO> postFindAll(){
        List<Post> posts =postRepository.findAllRead();


        return posts.stream()
                .map(post ->{
                    try{
                        if (true) { // 조건을 항상 참으로 설정하여 예외를 항상 발생시킵니다.
                            throw new RuntimeException("고의로 발생시킨 예외입니다.");
                        }
                        return new PostFindDTO(post.getId(), post.getTitle(), post.getMember().getEmail());
                    } catch (NullPointerException e) {
                        throw new NotFoundPostException("게시물을 불러오던 중 문제가 발생했습니다.", e);
                    } catch (Exception e){
                        throw new NotFoundPostException("게시물을 불러오던 중 문제가 발생했습니다.", e);
                    }

                })
                .collect(Collectors.toList());
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
