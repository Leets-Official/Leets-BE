package land.leets.domain.mail.domain;

import jakarta.persistence.*;
import land.leets.domain.shared.BaseTimeEntity;
import lombok.*;

@Entity(name = "recruit_mails")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecruitMail extends BaseTimeEntity {

    @Id
    @Column(nullable = false)
    private String email;
}
