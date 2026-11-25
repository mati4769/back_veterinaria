package com.proyecto.veterinaria.config;

import com.proyecto.veterinaria.security.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // usa la configuracion de CorsConfig (para que el frontend React pueda llamar al backend)
                .cors(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // sin token: el login, Swagger y la pagina de error
                        .requestMatchers("/api/v1/auth/**", "/swagger-ui/**", "/swagger-ui.html",
                                "/v3/api-docs/**", "/error").permitAll()
                        // sin token: ver el catalogo de productos (la pagina Productos es publica)
                        .requestMatchers(HttpMethod.GET, "/api/v1/productos/**").permitAll()
                        // todo lo demas necesita token
                        .anyRequest().authenticated())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
