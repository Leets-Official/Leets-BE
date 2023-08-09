package land.leets.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import land.leets.domain.auth.presentation.dto.OAuthTokenDto;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.global.jwt.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

@Service
public class AuthService {
    private final String googleAuthUrl;
    private final String googleRedirectUrl;
    private final String googleClientId;
    private final String googleClientPassword;
    private final UserRepository userRepository;

    @Autowired
    public AuthService(@Value("${google.auth.url}") String googleAuthUrl,
                       @Value("${google.redirect.url}") String googleRedirectUrl,
                       @Value("${spring.security.oauth2.client.registration.google.client-id}") String googleClientId,
                       @Value("${spring.security.oauth2.client.registration.google.client-secret}") String googleClientPassword,
                       UserRepository userRepository) {
        this.googleAuthUrl = googleAuthUrl;
        this.googleRedirectUrl = googleRedirectUrl;
        this.googleClientId = googleClientId;
        this.googleClientPassword = googleClientPassword;
        this.userRepository = userRepository;
    }

    public OAuthTokenDto getGoogleToken(String accessCode) {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", accessCode);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientPassword);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<OAuthTokenDto> responseEntity = restTemplate.postForEntity(googleAuthUrl, params, OAuthTokenDto.class);

        return Objects.requireNonNull(responseEntity.getBody());
    }

    private String decryptBase64UrlToken(String jwtToken) {
        byte[] decode = new Base64UrlCodec().decode(jwtToken);
        return new String(decode, StandardCharsets.UTF_8);
    }

    private User transJsonToUser(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            User user = mapper.readValue(json, User.class);

            Optional<User> bySub = userRepository.findBySub(user.getSub());
            return bySub.orElseGet(() -> userRepository.save(user));

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        User user = User.builder()
                .sub(userId)
                .name((String) payload.get("name"))
                .email(payload.getEmail())
                .build();

        return userRepository.save(user);
    }
}