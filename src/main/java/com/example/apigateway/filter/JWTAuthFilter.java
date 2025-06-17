package com.example.apigateway.filter;

import com.example.apigateway.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class JWTAuthFilter implements WebFilter {

    @Autowired
    JWTUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String token = getToken(exchange.getRequest());
        if (token != null && !jwtUtil.isExpired(token)) {
            String username = jwtUtil.extractUsername(token);
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    username, token, List.of()
            );
            return chain.filter(exchange).contextWrite(
                    ReactiveSecurityContextHolder.withAuthentication(auth));
        }
        return chain.filter(exchange);
    }

    private String getToken(ServerHttpRequest request) {
        String authHeader = request.getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
