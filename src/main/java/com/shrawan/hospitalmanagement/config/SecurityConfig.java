package com.shrawan.hospitalmanagement.config;

import com.shrawan.hospitalmanagement.security.JwtAuthenticationEntryPoint;
import com.shrawan.hospitalmanagement.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
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

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // Public & Health Check Endpoints
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/actuator/**", "/health").permitAll()

                        // Admin Endpoints
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/doctors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/doctors/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/departments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/departments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/departments/**").hasRole("ADMIN")

                        // Doctor Specific Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/prescriptions/**").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/prescriptions/**").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/medical-records/**").hasAnyRole("DOCTOR", "ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/medical-records/**").hasAnyRole("DOCTOR", "ADMIN")

                        // Receptionist Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/patients/**").hasAnyRole("RECEPTIONIST", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/bills/**").hasAnyRole("RECEPTIONIST", "ADMIN")

                        // Patient & Common Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/payments/**").hasAnyRole("PATIENT", "RECEPTIONIST", "ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasAnyRole("ADMIN", "DOCTOR", "RECEPTIONIST")

                        // All other requests require authentication
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
