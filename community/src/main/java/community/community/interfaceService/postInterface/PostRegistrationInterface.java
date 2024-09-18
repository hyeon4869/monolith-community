package community.community.interfaceService.postInterface;

import community.community.dto.postDTO.PostDTO;
import community.community.entity.Post;

public interface PostRegistrationInterface {
    Post postRegistration(PostDTO postDTO);
}
