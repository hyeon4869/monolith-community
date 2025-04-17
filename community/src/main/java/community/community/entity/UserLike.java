package community.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "user_like"
,indexes = @Index(name = "idx_unique_member_entity", columnList = "member_Id, entityId", unique = true))
public class UserLike extends BasicTimeEntity{

    @Id @GeneratedValue
    @Column(name = "userLike_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EntityName entityName;

    @Column(nullable = false)
    private Long entityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; // 좋아요 누른 회원 정보
}
