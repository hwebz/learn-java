package com.example.shopapp.filters;

import com.example.shopapp.components.JwtTokenUtil;
import com.example.shopapp.models.User;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.modelmapper.internal.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    @Value("${api.prefix}")
    private String apiPrefix;

    private final UserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(apiPrefix + "/products", "GET"),
                Pair.of(apiPrefix + "/categories", "GET"),
                Pair.of(apiPrefix + "/users/login", "POST"),
                Pair.of(apiPrefix + "/users/register", "POST")
        );

        if (isBypassToken(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            final String token = authHeader.replace("Bearer ", "");
            final String phoneNumber = jwtTokenUtil.getPhoneNunber(token);
            if (phoneNumber == null) {
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            }
            if (
                phoneNumber != null &&
                SecurityContextHolder.getContext().getAuthentication() == null
            ) {
                User userDetails = (User) userDetailsService.loadUserByUsername(phoneNumber);
                if (jwtTokenUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }

    private boolean isBypassToken(@NonNull HttpServletRequest request) {
        final List<Pair<String, String>> bypassTokens = Arrays.asList(
                Pair.of(apiPrefix + "/products", "GET"),
                Pair.of(apiPrefix + "/categories", "GET"),
                Pair.of(apiPrefix + "/users/login", "POST"),
                Pair.of(apiPrefix + "/users/register", "POST")
        );

        for (Pair<String, String> bypassToken: bypassTokens) {
            if (
                    request.getServletPath().contains(bypassToken.getLeft()) &&
                            request.getMethod().equals(bypassToken.getRight())
            ) {
                return true;
            }
        }

        return false;
    }
}
