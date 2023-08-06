package land.leets.global.config;

import land.leets.domain.shared.AuthRole;
import land.leets.global.filter.ExceptionHandleFilter;
import land.leets.global.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin().disable()
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //요청에 대한 권한 설정
        http.authorizeHttpRequests()
                .requestMatchers(CorsUtils::isCorsRequest).permitAll()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                .requestMatchers("/oauth2/**", "/auth/**").permitAll()
                .requestMatchers("/user/login", "/admin/login").permitAll()
                .requestMatchers("/user/me").permitAll()
                .requestMatchers("/login/user").hasAnyAuthority(AuthRole.ROLE_USER.getRole())
                .requestMatchers("/login/oauth2/callback/*").permitAll()
                .requestMatchers("/oauth2/authorization/*").permitAll()
                .requestMatchers("/**").permitAll()
                .anyRequest().authenticated();

        //oauth2Login
        http.oauth2Login();

        //jwt filter 설정
        http.addFilterBefore(new JwtFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandleFilter(), JwtFilter.class);


        return http.build();
    }
}
