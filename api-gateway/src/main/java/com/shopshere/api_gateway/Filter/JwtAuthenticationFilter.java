package com.shopshere.api_gateway.Filter;

import com.shopshere.api_gateway.Exception.JwtAuthenticationException;
import com.shopshere.api_gateway.Service.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.util.Collections;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        if (path.startsWith("/auth")
                || path.startsWith("/swagger-ui")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/product-service/v3/api-docs")
                || path.startsWith("/category-service/v3/api-docs")
                || path.startsWith("/auth-service/v3/api-docs")
                || path.startsWith("/cart-service/v3/api-docs")
                || path.startsWith("/payment-service/v3/api-docs")
                || path.startsWith("/inventory-service/v3/api-docs")
                || path.startsWith("/order-service/v3/api-docs")) {

            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new JwtAuthenticationException("Missing or invalid Authorization header.");
        }

        String token = authHeader.substring(7);

        if(!jwtUtil.validateToken(token)){
            throw new JwtAuthenticationException("Invalid or expired token.");
        }

        String username = jwtUtil.extractUsername(token);

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        Collections.emptyList()
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
