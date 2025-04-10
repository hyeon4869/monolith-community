package community.community.service.postService;

import community.community.dto.postDTO.PostFindDTO;
import community.community.exception.customException.DBAccessException;
import community.community.repository.postRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostSearchServiceImp implements PostSearchService{

    private final PostRepository postRepository;


    //제목 검색을 통한 조회
    @Override
    @Cacheable(value = "myC")
    public Page<PostFindDTO> postSearch(Pageable pageable, String title) {
        try{
            Page<PostFindDTO> searchTitle = postRepository.findSearchTitle(pageable, title);

            return searchTitle;
        } catch (DBAccessException e){
            throw new DBAccessException("DB와 관련된 알 수 없는 문제가 발생했습니다", e);
        } catch (Exception e) {
            throw new RuntimeException("알 수 없는 문제가 발생했습니다.");
        }

    }
}
