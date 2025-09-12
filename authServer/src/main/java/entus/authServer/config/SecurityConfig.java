package entus.authServer.config;

import entus.authServer.exception.CustomAccessDeniedHandler;
import entus.authServer.exception.CustomAuthenticationEntryPoint;
import entus.authServer.filter.AccessTokenValidFilter;
import entus.authServer.service.handler.CustomFailureHandler;
import entus.authServer.service.handler.CustomSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomSuccessHandler customSuccessHandler;
    private final CustomFailureHandler customFailureHandler;
    private final AccessTokenValidFilter accessTokenValidFilter;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Value("${resource.servers}")
    private final List<String> resourceServers;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((configurer) -> configurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(EndpointRequest.to("prometheus")).permitAll()
                        .requestMatchers("/api/**").authenticated()
                        .requestMatchers("/actuator/health").permitAll()
                        .requestMatchers("/js/**", "/css/**", "/images/**", "/favicon.ico").permitAll()
                        .requestMatchers("/", "/login", "/login/form-valid","/login/login-process", "/register", "/home", "/token/refresh").permitAll()
                        .requestMatchers("/public").permitAll()
                        .requestMatchers("/user").hasRole("USER")
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .successHandler(customSuccessHandler)
                        .failureHandler(customFailureHandler)
                        .loginPage("/login")
                        .loginProcessingUrl("/login/login-process"))

                .oauth2Login(auth -> auth
                        .successHandler(customSuccessHandler)
                        .failureHandler(customFailureHandler)
                        .loginPage("/login"))

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )

                .addFilterBefore(accessTokenValidFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * 리소스 서버 등록하는 Bean!!!!!
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(resourceServers);
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
