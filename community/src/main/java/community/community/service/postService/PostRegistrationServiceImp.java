package community.community.service.postService;

import community.community.dto.postDTO.PostDTO;
import community.community.dto.postDTO.PostRegisterDTO;
import community.community.entity.Member;
import community.community.entity.Post;
import community.community.exception.customException.DBAccessException;
import community.community.exception.customException.NotFoundMemberException;
import community.community.mapper.PostMapper;
import community.community.repository.memberRepository.MemberRepository;
import community.community.repository.postRepository.PostFileRepository;
import community.community.repository.postRepository.PostRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostRegistrationServiceImp implements PostRegistrationService {

    private final PostRepository postRepository;
    private final PostFileRepository postFileRepository;
    private final MemberRepository memberRepository;


    //게시글 등록
    @Transactional
    public PostRegisterDTO postRegistration(PostDTO postDTO, HttpSession session) {
        //게시물 등록 로직

        String loginEmail = (String) session.getAttribute("loginEmail");

        Member member = memberRepository.findByEmail(loginEmail)
                .orElseThrow(() -> new NotFoundMemberException("로그인 상태를 확인해주세요"));

        try {
            Post post = PostMapper.toEntity(postDTO);
            post.setMember(member);

            if(postDTO.getFile()!=null && !postDTO.getFile().isEmpty()){
                processFile(postDTO, post);
            }

            postRepository.save(post);
            return PostMapper.toPostRegisterDTO(post,"게시물을 등록했습니다.");
        }
          catch (DataAccessException e){
            throw new DBAccessException("게시글 등록 중 데이터베이스에 문제가 발생했습니다.",e);
        } catch (Exception e) {
            throw new RuntimeException("게시글 등록 중 예상치 못한 문제가 발생했습니다.");
        }
    }

    //파일 저장 메서드
    private void processFile(PostDTO postDTO, Post post) throws IOException {
        MultipartFile file = postDTO.getFile();

        String fileName = file.getOriginalFilename();
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        String uploadDir = "D:"+File.separator+"boot";
        Path filePath = Paths.get(uploadDir).resolve(uniqueFileName).normalize();
        String filePathStr = filePath.toString();
        //디렉토리가 없으면 생성
        Files.createDirectories(filePath.getParent());

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        postFileRepository.save(PostMapper.toEntity(fileName, filePathStr, post));

    }
}
