package community.community.mapper;

import community.community.dto.likeDTO.LikePostDTO;
import community.community.entity.EntityName;
import community.community.entity.Member;
import community.community.entity.UserLike;

public class UserLikeMapper {


    //////////////////////////////////////////////
    //엔티티로 변환하는 로직

    public static UserLike toEntity(LikePostDTO likePostDTO, Member member, EntityName entityName){
        return UserLike.builder()
                    .entityId(likePostDTO.getEntityId())
                    .member(member)
                    .entityName(entityName)
                    .build();
    }
}
