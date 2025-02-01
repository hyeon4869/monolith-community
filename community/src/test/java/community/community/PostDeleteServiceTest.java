package community.community;

import community.community.entity.Post;
import community.community.repository.postRepository.PostRepository;
import community.community.service.postService.PostDeleteServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class PostDeleteServiceTest {


    @InjectMocks
    private PostDeleteServiceImp postDeleteServiceImp;

    @Mock
    private PostRepository postRepository;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deleteService(){
        Long postId = 1L;
        Post mockPost = new Post();

        when(postRepository.findById(postId)).thenReturn(Optional.of(mockPost));

        postDeleteServiceImp.Delete(postId);

        assertTrue(mockPost.isDeleted());
        verify(postRepository, times(1)).findById(postId);
    }
}
