package community.community.service.likeService;

import community.community.dto.likeDTO.LikePostDTO;
import community.community.entity.EntityName;
import community.community.entity.Member;
import community.community.entity.Post;
import community.community.entity.UserLike;
import community.community.repository.likeRepository.LikeRepository;
import community.community.service.memberService.MemberFindService;
import community.community.service.postService.PostFindService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class LikePostServiceImp implements LikeService{

    @Autowired
    private final LikeRepository likeRepository;

    @Autowired
    private final MemberFindService memberFindService;

    @Autowired
    private final PostFindService postFindService;

    public LikePostServiceImp(LikeRepository likeRepository, MemberFindService memberFindService, PostFindService postFindService){
        this.likeRepository=likeRepository;
        this.memberFindService=memberFindService;
        this.postFindService=postFindService;
    }

    @Override
    public void likePush( LikePostDTO likePostDTO, HttpSession session,EntityName entityName) {
        String email = (String) session.getAttribute("loginEmail");
        Member member = memberFindService.findByEmail(email);

        Optional<UserLike> existingLike = likeRepository.findByMemberIdAndEntityId(member.getId(),likePostDTO.getEntityId());

        if(existingLike.isPresent()){
            if (entityName==EntityName.POST){
                Post post=postFindService.postFindId(likePostDTO.getEntityId());
                if(post.getLikeCount()>0){
                    post.decreaseLikeCount();
                }

            }

            likeRepository.delete(existingLike.get());
        }

        else{
            //like 저장
            UserLike userlike = UserLike.builder()
                .entityId(likePostDTO.getEntityId())
                .member(member)
                .entityName(entityName)
                .build();

            if (entityName==EntityName.POST){
                Post post=postFindService.postFindId(likePostDTO.getEntityId());
                post.increaseLikeCount();
            }
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
