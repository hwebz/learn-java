package com.example.shopapp.configurations;

import com.example.shopapp.filters.JwtTokenFilter;
import com.example.shopapp.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final JwtTokenFilter jwtTokenFilter;
    @Bean
    public SecurityFilterChain securityWebFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests.requestMatchers(
                        apiPrefix + "/users/register",
                        apiPrefix + "/users/login"
                    )
                    .permitAll()

                    .requestMatchers(HttpMethod.GET, apiPrefix + "/categories?**").hasAnyRole(Role.USER, Role.ADMIN)
                    .requestMatchers(HttpMethod.POST, apiPrefix + "/categories/**").hasRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.DELETE, apiPrefix + "/categories/**").hasRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.PUT, apiPrefix + "/categories/**").hasRole(Role.ADMIN)

                    .requestMatchers(HttpMethod.PUT, apiPrefix + "/orders/**").hasRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.POST, apiPrefix + "/orders/**").hasRole(Role.USER)
                    .requestMatchers(HttpMethod.DELETE, apiPrefix + "/orders/**").hasRole(Role.ADMIN)
                    .requestMatchers(HttpMethod.GET, apiPrefix + "/orders/**").hasAnyRole(Role.USER, Role.ADMIN)

                            .anyRequest()
                            .authenticated();
                });
        return http.build();
    }
}
