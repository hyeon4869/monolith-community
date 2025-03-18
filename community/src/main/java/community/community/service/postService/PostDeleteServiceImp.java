package community.community.service.postService;

import community.community.entity.Post;
import community.community.exception.customException.DBAccessException;
import community.community.repository.postRepository.PostRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostDeleteServiceImp implements PostDeleteService{

    private final PostRepository postRepository;

    @Override
    @Transactional
    public void Delete(Long id, HttpSession session) {
        Post post = postRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 게시물입니다."));

        if(post.isDeleted()){
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }

        String email = (String)session.getAttribute("loginEmail");
        try{
            if(post.getMember().getEmail().equals(email)){
                post.setIsDeleted();
            }

        } catch (DataAccessException e){
            throw new DBAccessException("데이터베이스 오류로 게시물 삭제를 수행할 수 없습니다.",e);
        } catch (RuntimeException e){
            throw new RuntimeException("서버 오류로 게시물 삭제를 수행할 수 없습니다.",e);
        }

    }

}
