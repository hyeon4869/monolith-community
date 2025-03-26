package community.community.controller.postController;

import community.community.dto.postDTO.PostDTO;
import community.community.dto.postDTO.PostRegisterDTO;
import community.community.service.postService.PostRegistrationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostRegistrationController {

    private final PostRegistrationService postRegistrationService;

    //게시글 등록
    @PostMapping("/postRegistration")
    public ResponseEntity<PostRegisterDTO> postRegistration(@RequestBody PostDTO postDTO, HttpSession session) {

        PostRegisterDTO postRegisterDTO = postRegistrationService.postRegistration(postDTO, session);

        return ResponseEntity.ok(postRegisterDTO);
    }
}
