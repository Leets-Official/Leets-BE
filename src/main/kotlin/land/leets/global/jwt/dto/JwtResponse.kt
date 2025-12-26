package land.leets.global.jwt.dto

data class JwtResponse(
    val accessToken: String,
    val refreshToken: String?
)
