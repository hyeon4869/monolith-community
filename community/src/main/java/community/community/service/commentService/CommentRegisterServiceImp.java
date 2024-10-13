package community.community.service.commentService;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.entity.Comment;
import community.community.entity.Post;
import community.community.exception.customException.DBAccessException;
import community.community.repository.commentRepository.CommentRepository;
import community.community.repository.postRepository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CommentRegisterServiceImp implements CommentRegisterService {

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final PostRepository postRepository;


    public CommentRegisterServiceImp(CommentRepository commentRepository, PostRepository postRepository){
        this.commentRepository=commentRepository;
        this.postRepository=postRepository;
    }


    @Override
    @Transactional
    public Comment register(CommentRegisterDTO commentRegisterDTO, String writer, Long id) {

        Post post = postRepository.findByReadId(id)
                .orElseThrow(() ->new IllegalArgumentException("해당 게시글이 존재하지 않습니다."));

        commentRegisterDTO.setWriter(writer);

        try{

        Comment comment= Comment.builder()
                .content(commentRegisterDTO.getContent())
                .writer(commentRegisterDTO.getWriter())
                .build();

        comment.setPost(post);

        commentRepository.save(comment);
        return comment;
        } catch (DataAccessException e){
            throw  new DBAccessException("댓글 생성 중 데이버베이스에 문제가 발생했습니다.",e);
        } catch (Exception e){
            throw new RuntimeException("예상치 못한 문제가 발생했습니다.");
        }
    }
}
