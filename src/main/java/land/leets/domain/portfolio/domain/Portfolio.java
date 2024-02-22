package land.leets.domain.portfolio.domain;

import jakarta.persistence.*;
import land.leets.domain.admin.domain.Admin;
import land.leets.domain.shared.BaseTimeEntity;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "portfolios")
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Portfolio extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long generation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String summary;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private ProjectType type;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column
    private String logoImgUrl;

    @Column
    private String coverImgUrl;

    @Column
    private String mainImgUrl;

}

