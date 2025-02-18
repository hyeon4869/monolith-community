package community.community.service.commentService;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.entity.Comment;
import community.community.entity.Post;
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
                .build();

        commentRepository.save(comment);
        commentRegisterDTO.setContent(comment.getContent());
        return commentRegisterDTO.getContent();
    }
}
