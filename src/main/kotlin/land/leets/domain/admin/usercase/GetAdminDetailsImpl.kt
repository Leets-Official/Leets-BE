package land.leets.domain.admin.usercase

import land.leets.domain.admin.domain.repository.AdminRepository
import land.leets.domain.admin.exception.AdminNotFoundException
import land.leets.domain.admin.presentation.dto.AdminDetailsResponse
import land.leets.domain.auth.AuthDetails
import org.springframework.stereotype.Service

@Service
class GetAdminDetailsImpl(
    private val adminRepository: AdminRepository
) : GetAdminDetails {

    override fun execute(authDetails: AuthDetails): AdminDetailsResponse {
        val uid = authDetails.uid
        val admin = adminRepository.findById(uid).orElseThrow { AdminNotFoundException() }
        return AdminDetailsResponse.from(admin)
    }
}