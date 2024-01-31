package meu.booking_rebuild_ver2.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
/*
Config of sercurity on system
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/api/v2");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

//                        .requestMatchers("/auth/**").permitAll()
//                        .requestMatchers("/status/**")
//                        .hasAnyRole("ADMIN")
//                        .requestMatchers("/busTypes/**")
//                        .hasAnyRole("ADMIN")
//                        .requestMatchers("/busSeat/**")
//                        .hasAnyRole("ADMIN")
//                        .requestMatchers("/routes/**")
//                        .hasAnyRole("ADMIN")
//                        .requestMatchers("/time/**")
//                        .hasAnyRole("ADMIN")
//                        .requestMatchers("/routeTime/**").hasAnyRole("ADMIN")
//                        .requestMatchers("/price/**").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**")
                        .permitAll()

                        .requestMatchers("/auth/login", "/demo/**").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/busTypes/**" ,
                                "/busSeat/**",
                                "/routes/**",
                                "/time/**",
                                "/loyalty",
                                "/routeTime/**",
                                "/price/**",
                                "/customers/**",
                                "/routeTime/**" )
                        .hasAnyRole("SUPER_ADMIN","ADMIN")
                        .requestMatchers(HttpMethod.POST,
                                "/auth/register" )
                        .hasAnyRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.GET,
                                "/busTypes/**" ,
                                "/auth/profile/me",
                                "/busSeat/**",
                                "/routes/**",
                                "/time/**",
                                "/loyalty",
                                "/routeTime/**",
                                "/price/**" ,
                                "/customers/**",
                                "/routeTime/**" )
                        .hasAnyRole("SUPER_ADMIN","ADMIN")
                        .requestMatchers(HttpMethod.PUT,
                                "/loyalty/**" )
                        .hasAnyRole("SUPER_ADMIN","ADMIN")
                        .requestMatchers(HttpMethod.DELETE,
                                "/busTypes/**" ,
                                "/busSeat/**",
                                "/routes/**",
                                "/time/**",
                                "/loyalty",
                                "/routeTime/**" ,
                                "/price/**",
                                "/customers/**",
                                "/routeTime/**" )
                        .hasAnyRole("SUPER_ADMIN")
                        .requestMatchers(HttpMethod.GET, "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**")
                        .permitAll()
                        .anyRequest().authenticated())
        .exceptionHandling().authenticationEntryPoint((request, response, e) -> {
                    ErrorResponse errorResponse = new ErrorResponse("Access Denied (403 Forbidden) ",
                            HttpStatus.FORBIDDEN.value());
                    response.setContentType("application/json;charset=UTF-8");
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
                });
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement(s -> s.maximumSessions(2));
        return http.build();
    }
    private static class ErrorResponse {
        private String message;
        private int status;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public int getStatus() {
            return status;
        }
    }
}
