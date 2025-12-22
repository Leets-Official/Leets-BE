package land.leets.domain.contributor.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import land.leets.domain.portfolio.domain.Portfolio

@Entity(name = "contributors")
class Contributor(
    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val position: Position,

    @Column
    val githubUrl: String? = null,

    @Column
    val profileUrl: String? = null,

    @Column
    val profile: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    val id: Long = 0L,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "portfolioId")
    @JsonIgnore
    var portfolio: Portfolio? = null
) {
    fun updatePortfolio(portfolio: Portfolio) {
        this.portfolio = portfolio
    }
}
