package land.leets.domain.interview.domain;

import jakarta.persistence.*;
import land.leets.domain.application.domain.Application;
import land.leets.domain.interview.type.HasInterview;
import land.leets.domain.shared.BaseTimeEntity;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "interviews")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Interview extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Application application;

    @Builder.Default
    @Column(columnDefinition = "char(10)")
    @Enumerated(EnumType.STRING)
    private HasInterview hasInterview = HasInterview.PENDING;

    @Column(nullable = false)
    private LocalDateTime fixedInterviewDate;

    @Column(nullable = false)
    private String place;
}
