package community.community.service.likeService;

import community.community.dto.likeDTO.LikePostDTO;
import community.community.entity.*;
import community.community.repository.likeRepository.LikeRepository;
import community.community.service.NotificationService;
import community.community.service.commentService.CommentLikeUpdateService;
import community.community.service.commentService.CommentViewService;
import community.community.service.memberService.MemberFindService;
import community.community.service.postService.PostFindService;
import community.community.service.postService.PostLikeUpdateService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LikeServiceImp implements LikeService{

    private final LikeRepository likeRepository;
    private final MemberFindService memberFindService;
    private final PostFindService postFindService;
    private final NotificationService notificationService;
    private final PostLikeUpdateService postLikeUpdateService;
    private final CommentViewService commentViewService;
    private final CommentLikeUpdateService commentLikeUpdateService;


    @Override
    public void likePush( LikePostDTO likePostDTO, HttpSession session,EntityName entityName) {
        String email = (String) session.getAttribute("loginEmail");
        Member member = memberFindService.findByEmail(email);

        Optional<UserLike> existingLike = likeRepository.findByMemberIdAndEntityId(member.getId(),likePostDTO.getEntityId());

        if(existingLike.isPresent()){
            if (entityName==EntityName.POST){
                Post post=postFindService.postFindId(likePostDTO.getEntityId());
                if(post.getLikeCount()>0){
                    postLikeUpdateService.decreaseLikeCount(post.getId());
                }

            }

            if(entityName==EntityName.COMMENT){
                Comment comment = commentViewService.findId(likePostDTO.getEntityId());
                if(comment.getLikeCount()>0){
                    commentLikeUpdateService.decreaseLikeCount(comment.getId());
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
                postLikeUpdateService.increaseLikeCount(post.getId());
            }

            if (entityName==EntityName.COMMENT){
                Comment comment=commentViewService.findId(likePostDTO.getEntityId());
                commentLikeUpdateService.increaseLikeCount(comment.getId());
            }
            notificationService.createNotification(member.getEmail(), likePostDTO.getEntityId());
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
