package land.leets.domain.contributor.domain.repository

import land.leets.domain.contributor.domain.Contributor
import org.springframework.data.jpa.repository.JpaRepository

interface ContributorRepository : JpaRepository<Contributor, Long>
