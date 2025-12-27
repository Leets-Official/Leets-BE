package land.leets.domain.auth.presentation.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class OAuthTokenDto(
    @JsonProperty("access_token") val accessToken: String,
    @JsonProperty("expires_in") val expiresIn: String,
    val scope: String,
    @JsonProperty("token_type") val tokenType: String,
    @JsonProperty("id_token") val idToken: String
)
