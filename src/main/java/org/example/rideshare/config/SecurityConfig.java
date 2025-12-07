package org.example.rideshare.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Security configuration for the application.
 * Sets up JWT-based authentication and endpoint access rules.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF since we're using stateless JWT authentication
                .csrf(csrf -> csrf.disable())

                // No sessions - every request is authenticated via JWT
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configure endpoint access rules
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints - no authentication needed
                        .requestMatchers("/api/auth/**").permitAll()

                        // Driver-only endpoints
                        .requestMatchers("/api/v1/driver/**").hasAuthority("ROLE_DRIVER")

                        // User-only endpoints (passengers)
                        .requestMatchers("/api/v1/user/**").hasAuthority("ROLE_USER")

                        // Create ride - only for passengers
                        .requestMatchers(HttpMethod.POST, "/api/v1/rides").hasAuthority("ROLE_USER")

                        // Complete ride - both users and drivers can do this
                        .requestMatchers(HttpMethod.POST, "/api/v1/rides/*/complete")
                        .hasAnyAuthority("ROLE_USER", "ROLE_DRIVER")

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                // Add our JWT filter before Spring's default authentication filter
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // BCrypt encoder for password hashing
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
