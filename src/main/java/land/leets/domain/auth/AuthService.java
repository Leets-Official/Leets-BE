package land.leets.domain.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import land.leets.domain.auth.exception.PermissionDeniedException;
import land.leets.domain.auth.presentation.dto.OAuthTokenDto;
import land.leets.domain.user.domain.User;
import land.leets.domain.user.domain.repository.UserRepository;
import land.leets.global.jwt.exception.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public User getGoogleToken(String code) throws GeneralSecurityException, IOException {

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();

        params.put("code", code);
        params.put("client_id", googleClientId);
        params.put("client_secret", googleClientPassword);
        params.put("redirect_uri", googleRedirectUrl);
        params.put("grant_type", "authorization_code");

        ResponseEntity<OAuthTokenDto> responseEntity = restTemplate.postForEntity(googleAuthUrl, params, OAuthTokenDto.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null) {
            throw new PermissionDeniedException();
        }

        String idToken = responseEntity.getBody().getId_token();
        return getUser(idToken);
    }


    public User getUser(String idToken) throws GeneralSecurityException, IOException {

        final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(googleClientId))
                .build();

        GoogleIdToken googleIdToken = verifier.verify(idToken);
        if (idToken == null) {
            throw new InvalidTokenException();
        }

        Payload payload = googleIdToken.getPayload();

        String userId = payload.getSubject();
        Optional<User> bySub = userRepository.findBySub(userId);

        if (bySub.isPresent()) {
            return bySub.get();
        }

        User user = User.builder()
                .sub(userId)
                .name((String) payload.get("name"))
                .email(payload.getEmail())
                .build();

        return userRepository.save(user);
    }
}
