package land.leets.domain.portfolio.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import land.leets.domain.contributor.domain.Contributor;
import land.leets.domain.shared.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private Long portfolioId;

    @Column(nullable = false)
    private Long generation;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String summary;

    @Column(columnDefinition = "text", nullable = false)
    private String description;

    @Column(nullable = false)
    private ProjectType type;

    @Column(nullable = false)
    private ProjectScope scope;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String serviceUrl;

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    private List<Contributor> contributors = new ArrayList<>();

    @Column(nullable = false)
    private String logoImgName;

    @Column(nullable = false)
    private String mainImgName;

}
