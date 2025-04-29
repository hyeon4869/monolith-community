package community.community.service.postService;

import community.community.dto.postDTO.PostDTO;
import community.community.dto.postDTO.PostRegisterDTO;
import community.community.entity.Member;
import community.community.entity.Post;
import community.community.exception.customException.DBAccessException;
import community.community.exception.customException.NotFoundMemberException;
import community.community.exception.customException.NotMatchTypeException;
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

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        } catch (IOException e){
            throw new NotMatchTypeException("이미지 파일 형식이 아닙니다.", e);

        } catch (Exception e) {
            throw new RuntimeException("게시글 등록 중 예상치 못한 문제가 발생했습니다.");
        }
    }

    //파일 저장 메서드
    private void processFile(PostDTO postDTO, Post post) throws IOException {
        MultipartFile file = postDTO.getFile();

        String fileName = file.getOriginalFilename();

        if (!isImageFile(fileName)) {
            throw new IOException("이미지 파일 형식이 아닙니다.");
        }

        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        String uploadDir = "D:"+File.separator+"boot" + File.separator + "images";
        Path filePath = Paths.get(uploadDir).resolve(uniqueFileName).normalize();
        String filePathStr = filePath.toString();
        //디렉토리가 없으면 생성
        Files.createDirectories(filePath.getParent());

        // 이미지 압축 처리
        compressAndSaveImg(file.getInputStream(), filePathStr, getFormatName(fileName));

        postFileRepository.save(PostMapper.toEntity(fileName, filePathStr, post));

    }
    //파일 유형 확인 메서드
    private boolean isImageFile(String fileName) {
        if (fileName == null) {
            return false;
        }
        String lowerFileName = fileName.toLowerCase();
        return lowerFileName.endsWith(".png") || lowerFileName.endsWith(".jpeg") || lowerFileName.endsWith(".jpg");
    }

    //이미지 압축 및 저장 메서드
    private void compressAndSaveImg(InputStream inputStream, String outputPath, String formatName) throws IOException{

        //원본 이미지 읽기
        BufferedImage originalImage = ImageIO.read(inputStream);

        //압축할 이미지 생성
        BufferedImage compressImage = new BufferedImage(
          originalImage.getWidth(),
          originalImage.getHeight(),
          BufferedImage.TYPE_INT_RGB
        );

        //이미지 그리기
        compressImage.createGraphics().drawImage(originalImage, 0, 0, null);

        //압축 품질 설정
        float quality = 0.7f;

        // 파일 출력 스트림을 try-with-resources로 생성 (자동으로 닫힘)
        try(FileOutputStream outputStream = new FileOutputStream(outputPath)) {
            // 지정한 포맷(JPG, PNG 등)에 맞는 ImageWriter 객체를 가져옴
            ImageWriter writer = ImageIO.getImageWritersByFormatName(formatName).next();
            // 이미지 저장 시의 파라미터(압축 등) 설정 객체 생성
            ImageWriteParam param = writer.getDefaultWriteParam();

            // 만약 해당 포맷이 압축을 지원한다면
            if (param.canWriteCompressed()) {
                // 압축 모드를 명시적으로 설정
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                // 압축 품질(0.0~1.0) 지정 (값이 낮을수록 용량이 줄고 품질이 낮아짐)
                param.setCompressionQuality(quality);
            }

            // 이미지 출력 스트림을 try-with-resources로 생성 (자동으로 닫힘)
            try(ImageOutputStream imageOutputStream = ImageIO.createImageOutputStream(outputStream)) {
                // ImageWriter의 출력 대상을 imageOutputStream으로 지정
                writer.setOutput(imageOutputStream);
                // 압축된 이미지를 파일로 저장
                writer.write(null, new IIOImage(compressImage, null, null), param);
                // ImageWriter 자원 해제
                writer.dispose();
            }
        }

    }

    //파일 확장자 추출 메서드
    private String getFormatName(String fileName){
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension.toLowerCase();
    }
}
