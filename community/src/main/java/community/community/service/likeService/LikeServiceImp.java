package community.community.service.likeService;

import community.community.dto.likeDTO.LikePostDTO;
import community.community.entity.*;
import community.community.mapper.UserLikeMapper;
import community.community.repository.likeRepository.LikeRepository;
import community.community.service.commentService.CommentFindService;
import community.community.service.commentService.CommentLikeUpdateService;
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
//    private final NotificationService notificationService;
    private final PostLikeUpdateService postLikeUpdateService;
    private final CommentFindService commentFindService;
    private final CommentLikeUpdateService commentLikeUpdateService;

    //좋아요 버튼 눌렀을 때 실행
    @Override
    public void likePush( LikePostDTO likePostDTO, HttpSession session,EntityName entityName) {
        String email = (String) session.getAttribute("loginEmail");
        Member member = memberFindService.findByReadEmail(email);

        //같은 사용자가 이미 좋아요를 눌렀는지 확인
        Optional<UserLike> existingLike = likeRepository.findByMemberIdAndEntityId(member.getId(),likePostDTO.getEntityId());

        //존재하면 실행
        if(existingLike.isPresent()){
            //게시글일 경우
            if (entityName==EntityName.POST){
                Post post=postFindService.postFindId(likePostDTO.getEntityId());
                if(post.getLikeCount()>0){
                    postLikeUpdateService.decreaseLikeCount(post.getId());
                }

            }
            //댓글일 경우
            if(entityName==EntityName.COMMENT){
                Comment comment = commentFindService.findId(likePostDTO.getEntityId());
                if(comment.getLikeCount()>0){
                    commentLikeUpdateService.decreaseLikeCount(comment.getId());
                }
            }
            //만약 이미 존재한다면 좋아요 취소
            likeRepository.delete(existingLike.get());
        }

        else{
            //해당 게시물, 댓글에 대한 첫 좋아요 일 경우
            //like 저장
            UserLike userlike = UserLikeMapper.toEntity(likePostDTO, member, entityName);

            //게시글
            if (entityName==EntityName.POST){
                Post post=postFindService.postFindId(likePostDTO.getEntityId());
                postLikeUpdateService.increaseLikeCount(post.getId());
            }
            //댓글
            if (entityName==EntityName.COMMENT){
                Comment comment= commentFindService.findId(likePostDTO.getEntityId());
                commentLikeUpdateService.increaseLikeCount(comment.getId());
            }
            //알림 전송
//            notificationService.createNotification(member.getEmail(), likePostDTO.getEntityId());
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
