package land.leets.global.auth;

import land.leets.domain.shared.AuthRole;
import land.leets.domain.user.User;
import land.leets.domain.user.UserRepository;
import land.leets.global.auth.exception.OAuthProcessingException;
import land.leets.global.auth.type.AuthProvider;
import land.leets.global.auth.user.GoogleOAuth2UserInfo;
import land.leets.global.auth.user.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    // OAuth2UserRequest에 있는 Access Token으로 유저정보 get
    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest); // 유저정보

        try {
            return process(oAuth2UserRequest, oAuth2User);
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    // 획득한 유저정보를 Java Model과 맵핑하고 프로세스 진행
    private OAuth2User process(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        AuthProvider authProvider = AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId().toUpperCase());
        GoogleOAuth2UserInfo userInfo = new GoogleOAuth2UserInfo(oAuth2User.getAttributes());


        if (userInfo.getEmail().isEmpty()) {
            throw new OAuthProcessingException("Email not found from OAuth2 provider");
        }
        Optional<User> userOptional = userRepository.findByEmail(userInfo.getEmail());
        User user;

        if (userOptional.isPresent()) {		// 이미 가입된 경우
            user = userOptional.get();
            if (authProvider != user.getAuthProvider()) {
                throw new OAuthProcessingException("Wrong Match Auth Provider");
            }

        } else {			// 가입되지 않은 경우
            user = createUser(userInfo, authProvider);
        }
        return CustomUserDetails.create(user, oAuth2User.getAttributes());
    }

    private User createUser(OAuth2UserInfo userInfo, AuthProvider authProvider) {
        User user = User.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .role(AuthRole.ROLE_USER)
                .authProvider(authProvider)
                .build();
        return userRepository.save(user);
    }
}