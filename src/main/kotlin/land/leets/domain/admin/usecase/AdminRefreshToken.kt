package land.leets.domain.admin.usecase

import land.leets.global.jwt.dto.JwtResponse

interface AdminRefreshToken {
    fun execute(refreshToken: String): JwtResponse
}