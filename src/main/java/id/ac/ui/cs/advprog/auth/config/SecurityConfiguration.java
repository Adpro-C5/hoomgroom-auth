package id.ac.ui.cs.advprog.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DelegatedAuthenticationEntryPoint authEntryPoint) throws Exception {
        http
                .cors(Customizer.withDefaults()) // refer to a bean called corsConfigurationSource
                .csrf()
                .disable()
                .authorizeHttpRequests(
                        authorize ->
                                authorize.requestMatchers(HttpMethod.GET, "/api/**").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/actuator").permitAll()
                                        .requestMatchers(HttpMethod.GET, "/actuator/**").permitAll()
                                        .requestMatchers("/api/auth/**").permitAll()
                                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint);

        return http.build();
    }
}