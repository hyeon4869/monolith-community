package community.community.service.postService;

import community.community.dto.postDTO.PostDTO;
import community.community.dto.postDTO.PostRegisterDTO;
import community.community.entity.Member;
import community.community.entity.Post;
import community.community.exception.customException.DBAccessException;
import community.community.exception.customException.NotFoundMemberException;
import community.community.repository.memberRepository.MemberRepository;
import community.community.repository.postRepository.PostRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostRegistrationServiceImp implements PostRegistrationService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;


    //게시글 등록
    @Transactional
    public PostRegisterDTO postRegistration(PostDTO postDTO, HttpSession session) {
        //게시물 등록 로직

        String loginEmail = (String) session.getAttribute("loginEmail");

        Member member = memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new NotFoundMemberException("로그인 상태를 확인해주세요"));
        try {


            Post post = Post.builder()
                    .title(postDTO.getTitle())
                    .content(postDTO.getContent())
                    .build();


            post.setMember(member);
            postRepository.save(post);
            return PostRegisterDTO.toDTO(post,"게시물을 등록했습니다.");
        }
          catch (DataAccessException e){
            throw new DBAccessException("게시글 등록 중 데이터베이스에 문제가 발생했습니다.",e);
        } catch (Exception e) {
            throw new RuntimeException("게시글 등록 중 예상치 못한 문제가 발생했습니다.");
        }
    }
}
