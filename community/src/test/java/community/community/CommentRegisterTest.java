package community.community;

import community.community.dto.commentDTO.CommentRegisterDTO;
import community.community.repository.commentRepository.CommentRepository;
import community.community.service.commentService.CommentRegisterServiceImp;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

public class CommentRegisterTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentRegisterServiceImp commentRegisterServiceImp;;

    @Test
    void CommentRegister(){
        CommentRegisterDTO dto = new CommentRegisterDTO();

        dto.setContent(dto.getContent());
    }


}
