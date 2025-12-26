package land.leets.global.jwt

import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {
    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private val IGNORE_JWT_FILTER_API = listOf(
            "/user/login",
            "/admin/login",
            "/admin/refresh"
        )
    }

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI in IGNORE_JWT_FILTER_API) {
            return filterChain.doFilter(request, response)
        }

        resolveToken(request)?.let { token ->
            jwtProvider.validateToken(token, false)
            jwtProvider.getAuthentication(token).also { authentication ->
                SecurityContextHolder.getContext().authentication = authentication
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(AUTHORIZATION)
            ?.takeIf { it.startsWith(BEARER) && StringUtils.hasText(it) }
            ?.substringAfter("$BEARER ")
}
