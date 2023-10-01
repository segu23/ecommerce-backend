package org.kayteam.ecommerce.backend.auth;

import org.kayteam.ecommerce.backend.exceptions.ExpiredTokenException;
import org.kayteam.ecommerce.backend.models.Authority;
import org.kayteam.ecommerce.backend.models.TokenJWT;
import org.kayteam.ecommerce.backend.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenService tokenService;

    private final String[] staffRoles = {
            Authority.ADMIN.name(),
            Authority.OWNER.name()
    };

    public static String[] SWAGGER_URL_PATHS = new String[]{"/swagger-ui/index.html", "/swagger-resources/**",
            "/v2/api-docs/**", "/v3/api-docs/**", "/webjars/**", "/swaggerfox.js", "/swagger-ui/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                            .requestMatchers(jwtAuthFilter.getUnauthenticatedPaths().toArray(new String[]{})).permitAll()
                            .requestMatchers("/admin/**").hasAnyRole(staffRoles)
                            .anyRequest().authenticated()
                )
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/auth/sign-out")
                        .addLogoutHandler((request, response, authentication) -> {
                            final String authHeader = request.getHeader("Authorization");
                            final String jwt;

                            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                                return;
                            }

                            jwt = authHeader.substring(7);

                            TokenJWT storedToken = tokenService.findByToken(jwt);

                            if (storedToken != null) {
                                if (jwtService.isTokenExpired(jwt) || storedToken.getRevoked()) {
                                    throw new ExpiredTokenException();
                                }

                                storedToken.setRevoked(true);
                                tokenService.save(storedToken);

                                SecurityContextHolder.clearContext();
                            }
                        })
                        .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                )
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer
                        .configurationSource(request -> {
                            CorsConfiguration corsConfiguration = new CorsConfiguration();
                            corsConfiguration.addAllowedOriginPattern("*");
                            corsConfiguration.addAllowedHeader("*");
                            corsConfiguration.addAllowedMethod("*");
                            corsConfiguration.setAllowCredentials(true);
                            return corsConfiguration;
                        })
                ).build();
    }
}
