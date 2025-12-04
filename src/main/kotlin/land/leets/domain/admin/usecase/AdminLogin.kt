package land.leets.domain.admin.usecase

import land.leets.global.jwt.dto.JwtResponse

interface AdminLogin {
    fun execute(username: String, password: String): JwtResponse
}