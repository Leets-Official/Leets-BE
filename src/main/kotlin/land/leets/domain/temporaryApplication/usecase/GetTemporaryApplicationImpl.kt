package land.leets.domain.temporaryApplication.usecase

import land.leets.domain.auth.AuthDetails
import land.leets.domain.temporaryApplication.domain.repository.TemporaryApplicationRepository
import land.leets.domain.temporaryApplication.presentation.dto.TemporaryApplicationResponse
import org.springframework.stereotype.Service

@Service
class GetTemporaryApplicationImpl(
    private val temporaryApplicationRepository: TemporaryApplicationRepository
) : GetTemporaryApplication {

    override fun execute(authDetails: AuthDetails): TemporaryApplicationResponse? {
        return temporaryApplicationRepository.findByUser_Id(authDetails.uid)
            ?.let { TemporaryApplicationResponse.from(it) }
    }
}
