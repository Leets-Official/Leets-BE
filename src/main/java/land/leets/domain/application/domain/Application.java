package land.leets.domain.application.domain;


import jakarta.persistence.*;
import land.leets.domain.application.type.ApplicationStatus;
import land.leets.domain.application.type.Position;
import land.leets.domain.application.type.SubmitStatus;
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
    private String name;

    @Column(nullable = false)
    private String gpa;

    @Column(nullable = false)
    private String major;

    @Column(nullable = false)
    private String grade;

    @Column
    private String project;

    @Column
    private String algorithm;

    @Column
    private String portfolio;

    @Column(nullable = false, columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    private Position position;

    @Column
    private String career;

    @Column(nullable = false)
    private String interviewDay;

    @Column(nullable = false)
    private String interviewTime;

    @Column(nullable = false, columnDefinition = "text(700)")
    private String enhancement;

    @Column(nullable = false, columnDefinition = "text(500)")
    private String level;

    @Column(nullable = false, columnDefinition = "text(500)")
    private String pros;

    @Column(nullable = false, columnDefinition = "text(700)")
    private String goal;

    @Column(nullable = false, columnDefinition = "text(500)")
    private String completion;

    @Column
    private LocalDateTime appliedAt;

    @Builder.Default
    @Column(columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    private ApplicationStatus applicationStatus = ApplicationStatus.PENDING;

    @Column(columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    private SubmitStatus submitStatus;

    public void updateInfo(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
}
