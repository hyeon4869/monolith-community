package community.community.service.likeService;

import community.community.dto.likeDTO.LikePostDTO;
import community.community.entity.EntityName;
import community.community.entity.Member;
import community.community.entity.UserLike;
import community.community.repository.likeRepository.LikeRepository;
import community.community.service.memberService.MemberFindService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LikePostServiceImp implements LikeService{

    private final LikeRepository likeRepository;

    @Autowired
    private final MemberFindService memberFindService;

    public LikePostServiceImp(LikeRepository likeRepository, MemberFindService memberFindService){
        this.likeRepository=likeRepository;
        this.memberFindService=memberFindService;
    }

    @Override
    @Transactional
    public void likePush( LikePostDTO likePostDTO, HttpSession session,EntityName entityName) {
        String email = (String) session.getAttribute("loginEmail");
        Member member = memberFindService.findByEmail(email);

        Optional<UserLike> existingLike = likeRepository.findByMemberIdAndEntityId(member.getId(),likePostDTO.getEntityId());

        if(existingLike.isPresent()){
            likeRepository.delete(existingLike.get());
        }

        else{
            //like 저장
            UserLike userlike = UserLike.builder()
                .entityId(likePostDTO.getEntityId())
                .member(member)
                .entityName(entityName)
                .build();

//            System.out.println(entityName);

             likeRepository.save(userlike);
        }
    }

    @Override
    @Transactional
    public void likePostPush( LikePostDTO likePostDTO, HttpSession session, EntityName entityName) {
        likePush(likePostDTO, session, entityName);

    }


    @Override
    @Transactional
    public void likeCommentPush( LikePostDTO likePostDTO, HttpSession session, EntityName entityName) {
        likePush(likePostDTO, session, entityName);
    }

}
