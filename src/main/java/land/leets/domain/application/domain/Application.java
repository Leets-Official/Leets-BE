package land.leets.domain.application.domain;


import jakarta.persistence.*;
import land.leets.domain.shared.BaseTimeEntity;
import land.leets.domain.user.domain.User;
import lombok.*;

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
    private float gpa;

    @Column
    private String algorithm;

    @Column
    private String portfolio;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String interview;

    @Column(nullable = false)
    private String enhancement;

    @Column(nullable = false)
    private String level;

    @Column(nullable = false)
    private String pros;

    @Column(nullable = false)
    private String goal;

    @Column(nullable = false)
    private String completion;
}
