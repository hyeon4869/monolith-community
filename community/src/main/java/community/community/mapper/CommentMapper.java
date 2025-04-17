package community.community.mapper;

import community.community.dto.commentDTO.CommentFindDTO;
import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;
import community.community.entity.Member;
import community.community.entity.Post;

public class CommentMapper {

    public static RepliesCommentRegisterDTO toRepliesCommentRegisterDTO(Comment comment){
        return RepliesCommentRegisterDTO.builder()
                    .content(comment.getContent())
                    .createTime(comment.getCreateTime())
                    .build();

    }

    public static CommentFindDTO toCommentViewDTO(Comment comment) {
        return CommentFindDTO.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .writer(comment.getMember().getEmail())
                    .createTime(comment.getCreateTime())
                    .build();

    }

    //////////////////////////////////////////////
    //엔티티로 변환하는 로직

    public static Comment toEntity(RepliesCommentRegisterDTO repliesCommentRegisterDTO, Member member, Comment parentComment){
        return Comment.builder()
                    .content(repliesCommentRegisterDTO.getContent())
                    .member(member)
                    .parentComment(parentComment)
                    .post(parentComment.getPost())
                    .build();
    }

    public static Comment toEntity(CommentRegisterDTO commentRegisterDTO, Member member, Post post){
        return Comment.builder()
                    .content(commentRegisterDTO.getContent())
                    .member(member)
                    .post(post)
                    .parentComment(null)
                    .build();
    }

}
