package land.leets.domain.auth.presentation.dto;

import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class OAuthTokenDto {
    private String access_token;
    private String expires_in;
    private String scope;
    private String token_type;
    private String id_token;
}
