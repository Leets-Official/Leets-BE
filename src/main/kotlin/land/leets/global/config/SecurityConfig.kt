package land.leets.global.config

import land.leets.domain.auth.exception.PermissionDeniedException
import land.leets.domain.shared.AuthRole
import land.leets.global.filter.ExceptionHandleFilter
import land.leets.global.jwt.JwtFilter
import land.leets.global.jwt.JwtProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtProvider: JwtProvider
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http {
            httpBasic { disable() }
            formLogin { disable() }
            cors { }
            csrf { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }

        }

        http {
            exceptionHandling {
                authenticationEntryPoint = AuthenticationEntryPoint { _, _, _ -> throw PermissionDeniedException() }
                accessDeniedHandler = AccessDeniedHandler { _, _, _ -> throw PermissionDeniedException() }
            }
        }

        http {
            authorizeHttpRequests {

                // CORS preflight
                authorize(HttpMethod.OPTIONS, "/**", permitAll)

                // health check
                authorize(HttpMethod.GET, "/health-check", permitAll)

                // swagger
                authorize("/v3/api-docs/**", permitAll)
                authorize("/swagger-ui/**", permitAll)

                // oauth2
                authorize("/oauth2/**", permitAll)
                authorize("/auth/**", permitAll)
                authorize(HttpMethod.GET, "/login/oauth2/callback/google", permitAll)

                // auth
                authorize(HttpMethod.POST, "/user/login", permitAll)
                authorize(HttpMethod.POST, "/admin/login", permitAll)
                authorize(HttpMethod.POST, "/admin/refresh", permitAll)

                // user info
                authorize(HttpMethod.GET, "/user/me", hasAuthority(AuthRole.ROLE_USER.role))
                authorize(HttpMethod.GET, "/admin/me", hasAuthority(AuthRole.ROLE_ADMIN.role))

                // applications
                authorize(HttpMethod.GET, "/application", hasAuthority(AuthRole.ROLE_ADMIN.role))
                authorize(HttpMethod.POST, "/application", hasAuthority(AuthRole.ROLE_USER.role))
                authorize(HttpMethod.PATCH, "/application", hasAuthority(AuthRole.ROLE_USER.role))
                authorize(HttpMethod.GET, "/application/me", hasAuthority(AuthRole.ROLE_USER.role))
                authorize(HttpMethod.GET, "/application/{id}", hasAuthority(AuthRole.ROLE_ADMIN.role))
                authorize(HttpMethod.PATCH, "/application/{id}", hasAuthority(AuthRole.ROLE_ADMIN.role))

                // interviews
                authorize(HttpMethod.PATCH, "/interview", hasAuthority(AuthRole.ROLE_USER.role))
                authorize(HttpMethod.POST, "/interview/{id}", hasAuthority(AuthRole.ROLE_ADMIN.role))
                authorize(HttpMethod.PATCH, "/interview/{id}", hasAuthority(AuthRole.ROLE_ADMIN.role))

                // comments
                authorize(HttpMethod.POST, "/comments", hasAuthority(AuthRole.ROLE_ADMIN.role))
                authorize(HttpMethod.GET, "/comments/{applicationId}", hasAuthority(AuthRole.ROLE_ADMIN.role))

                // portfolios
                authorize(HttpMethod.GET, "/portfolios", permitAll)
                authorize(HttpMethod.GET, "/portfolios/{portfolioId}", permitAll)

                // images
                authorize(HttpMethod.GET, "/images/{imageName}", permitAll)

                // storages
                authorize(HttpMethod.POST, "/storages/pre-authenticated-url", hasAnyAuthority(AuthRole.ROLE_USER.role))

                // default
                authorize(anyRequest, denyAll)
            }
        }

        http {
            oauth2Login { }
        }

        http {
            addFilterBefore<UsernamePasswordAuthenticationFilter>(
                filter = JwtFilter(jwtProvider)
            )
            addFilterBefore<JwtFilter>(
                filter = ExceptionHandleFilter()
            )
        }

        return http.build()
    }
}
