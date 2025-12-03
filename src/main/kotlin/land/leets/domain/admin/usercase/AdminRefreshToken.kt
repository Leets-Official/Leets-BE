package land.leets.domain.admin.usercase

import land.leets.global.jwt.dto.JwtResponse

interface AdminRefreshToken {
    fun execute(refreshToken: String): JwtResponse
}