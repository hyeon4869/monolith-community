package community.community.service.postService;

import community.community.dto.postDTO.PostDTO;
import community.community.entity.Member;
import community.community.entity.Post;
import community.community.exception.customException.NotFoundMemberException;
import community.community.interfaceService.postInterface.PostRegistrationInterface;
import community.community.repository.MemberRepository;
import community.community.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostRegistrationService implements PostRegistrationInterface {

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final MemberRepository memberRepository;

    public PostRegistrationService(PostRepository postRepository, MemberRepository memberRepository){
        this.postRepository=postRepository;
        this.memberRepository=memberRepository;
    }


    @Transactional
    public Post postRegistration(PostDTO postDTO, HttpSession session){
        //게시물 등록 로직
        Post post = new Post();
        post.convertToPost(postDTO);
        String loginEmail = (String) session.getAttribute("loginEmail");

        Member member =  memberRepository.findByEmail(loginEmail)
                        .orElseThrow(() ->new NotFoundMemberException("로그인 상태를 확인해주세요"));

        post.setMember(member);
        postRepository.save(post);
        return post;
    }
}
