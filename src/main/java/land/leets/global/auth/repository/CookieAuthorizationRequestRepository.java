package land.leets.global.auth.repository;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import land.leets.global.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class CookieAuthorizationRequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public static final String OAUTH_2_AUTH_REQUEST = "oauth2_auth_request";
    public static final String REDIRECT_URI = "redirect_uri";
    private static final int COOKIE_EXPIRE_SECONDS = 180;
    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
         return CookieUtil.getCookie(request, OAUTH_2_AUTH_REQUEST)
                .map(cookie -> CookieUtil.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElse(null);
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request, HttpServletResponse response) {
        if (authorizationRequest == null) {
            CookieUtil.deleteCookie(request, response, OAUTH_2_AUTH_REQUEST);
            CookieUtil.deleteCookie(request, response, REDIRECT_URI);
            return;
        }

        CookieUtil.addCookie(response, OAUTH_2_AUTH_REQUEST, CookieUtil.serialize(authorizationRequest), COOKIE_EXPIRE_SECONDS);


        String redirectUriAfterLogin = request.getParameter(REDIRECT_URI);
        if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
            CookieUtil.addCookie(response, REDIRECT_URI, redirectUriAfterLogin, COOKIE_EXPIRE_SECONDS);
        }
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {
        return this.loadAuthorizationRequest(request);
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, OAUTH_2_AUTH_REQUEST);
        CookieUtil.deleteCookie(request, response, REDIRECT_URI);
    }
}