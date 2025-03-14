package community.community.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class Notification extends BasicTimeEntity{

    @Id
    @GeneratedValue
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
