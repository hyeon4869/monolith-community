package community.community.service.commentService;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.dto.commentDTO.RepliesCommentRegisterDTO;
import community.community.entity.Comment;
import community.community.entity.Post;
import community.community.exception.customException.DBAccessException;
import community.community.repository.commentRepository.CommentRepository;
import community.community.service.postService.PostFindService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentRegisterServiceImp implements CommentRegisterService{

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final PostFindService postFindService;

    public CommentRegisterServiceImp(CommentRepository commentRepository, PostFindService postFindService){
        this.commentRepository=commentRepository;
        this.postFindService=postFindService;
    }

    @Transactional
    @Override
    public String commentRegister(Long id, CommentRegisterDTO commentRegisterDTO, HttpSession session) {
        String writer=(String)session.getAttribute("loginEmail");

        if(writer==null||writer.isEmpty()){
            writer="익명";
        }

        //게시물 존재여부 확인
        Post post = postFindService.postFindId(id);

        Comment comment = Comment.builder()
                .content(commentRegisterDTO.getContent())
                .writer(writer)
                .post(post)
                .parentComment(null)
                .build();

        try {
            commentRepository.save(comment);
        } catch (DBAccessException e){
            throw new DBAccessException("DB와의 연결에 문제가 발생했습니다.",e);
        }
        return commentRegisterDTO.getContent();
    }

    @Override
    @Transactional
    public String replicaCommentRegister(Long commentId, RepliesCommentRegisterDTO repliesCommentRegisterDTO, HttpSession session) {
        String writer=(String)session.getAttribute("loginEmail");

        if(writer==null||writer.isEmpty()){
            writer="익명";
        }

        //댓글 존재여부 확인
        Comment parentComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("조회되는 댓글이 없습니다."));


        Comment comment = Comment.builder()
                .content(repliesCommentRegisterDTO.getContent())
                .writer(writer)
                .parentComment(parentComment)
                .post(parentComment.getPost())
                .build();

        try{
            commentRepository.save(comment);
            return repliesCommentRegisterDTO.getContent();
        } catch (DBAccessException e){
            throw new DBAccessException("DB와의 연결에 문제가 발생했습니다.",e);
        }


    }
}
