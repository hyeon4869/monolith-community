package community.community.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
public class Notification extends BasicTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    private String message;

    private String targetEmail; // 추가된 필드

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void setMember(Member member){
        this.member=member;
    }
}
