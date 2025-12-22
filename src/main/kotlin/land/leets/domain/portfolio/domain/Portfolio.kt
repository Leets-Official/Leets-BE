package land.leets.domain.portfolio.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import land.leets.domain.contributor.domain.Contributor
import land.leets.domain.portfolio.type.ProjectScope
import land.leets.domain.portfolio.type.ProjectType
import land.leets.domain.shared.BaseTimeEntity
import java.time.LocalDate

@Entity(name = "portfolios")
class Portfolio(
    @Column(nullable = false)
    val generation: Long,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val summary: String,

    @Column(columnDefinition = "text", nullable = false)
    val description: String,

    @Column(nullable = false)
    val type: ProjectType,

    @Column(nullable = false)
    val scope: ProjectScope,

    @Column(nullable = false)
    val startDate: LocalDate,

    @Column(nullable = false)
    val endDate: LocalDate,

    @Column(nullable = false)
    val serviceUrl: String,

    @Column(nullable = false)
    val logoImgName: String,

    @Column(nullable = false)
    val mainImgName: String,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val portfolioId: Long = 0L,

    @OneToMany(mappedBy = "portfolio", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    val contributors: ArrayList<Contributor> = arrayListOf()
) : BaseTimeEntity()
