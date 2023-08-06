package land.leets.domain.application.domain;


import jakarta.persistence.*;
import land.leets.domain.shared.BaseTimeEntity;
import land.leets.domain.user.domain.User;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "applications")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Application extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "uid")
    private User user;

    @Column(nullable = false)
    private float score;

    @Column
    private String algorithm;

    @Column
    private String portfolio;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private LocalDateTime meetingAt;

}
