package land.leets.global.auth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import land.leets.domain.user.UserRepository;
import land.leets.global.auth.exception.BadRequestException;
import land.leets.global.auth.repository.CookieAuthorizationRequestRepository;
import land.leets.global.jwt.JwtProvider;
import land.leets.global.jwt.JwtTokenProvider;
import land.leets.global.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import static land.leets.global.auth.repository.CookieAuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Log4j2
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${jwt.oauth2.authorizedRedirectUri}")
    private String redirectUri;
    //    private final JwtProvider jwtProvider;
    private final JwtTokenProvider jwtProvider;
    private final CookieAuthorizationRequestRepository authorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);
        log.info("targetUrl: "+ targetUrl);

        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtil.getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)
                .map(Cookie::getValue);

        if (redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("redirect URIs are not matched");
        }
        log.info("redirectUri: " + redirectUri);
        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        // JWT 생성
        String accessToken = jwtProvider.generateToken(authentication);
        jwtProvider.createRefreshToken(authentication, response);

        log.info("accessToken "+accessToken);

        return UriComponentsBuilder.fromUriString(targetUrl).build().encode().toUriString();
//        return UriComponentsBuilder.fromUriString(targetUrl).queryParam("accessToken", accessToken).build().encode().toUriString();

    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        authorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }


    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);
        URI authorizedUri = URI.create(redirectUri);

        if (authorizedUri.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                && authorizedUri.getPort() == clientRedirectUri.getPort()) {
            return true;
        }
        return false;
    }
}